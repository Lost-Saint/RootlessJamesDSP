package app.siphondsp.session.root

import android.content.Context
import android.content.Intent
import android.media.audiofx.AudioEffect
import kotlinx.coroutines.Job
import app.siphondsp.MainApplication
import app.siphondsp.interop.JamesDspRemoteEngine
import app.siphondsp.interop.ProcessorMessageHandler
import app.siphondsp.model.AudioSessionDumpEntry
import app.siphondsp.model.IEffectSession
import app.siphondsp.model.root.RemoteEffectSession
import app.siphondsp.session.shared.BaseSessionDatabase
import app.siphondsp.utils.extensions.ContextExtensions.getUidFromPackage
import app.siphondsp.utils.extensions.ContextExtensions.toast
import timber.log.Timber


class RootSessionDatabase(context: Context) : BaseSessionDatabase(context) {

    private val app
        get() = context.applicationContext as MainApplication

    var enabled: Boolean = true
        set(value) {
            field = value
            sessionList.forEach { (_, session) ->
                (session as RemoteEffectSession).effect?.enabled = value
            }
        }

    override fun createSession(id: Int, uid: Int, packageName: String): IEffectSession? {
        if (sessionList[id] == null) {
            Timber.d("Creating effect for session $id (${packageName})")

            val effect = try {
                JamesDspRemoteEngine(context, id, 0, ProcessorMessageHandler())
            }
            catch (ex: Exception) {
                Timber.e("Failed to instantiate JamesDSP effect for session $id ($packageName)")
                Timber.e(ex.cause)

                // Debug toast
                context.toast("JDSP load fail (session=$id): " + ex.message)
                return null
            }

            effect.syncWithPreferences()
            effect.enabled = enabled

            val finalUid = if (uid >= 0)
                uid
            else
                context.getUidFromPackage(packageName)

            return RemoteEffectSession(packageName, finalUid, effect)
        } else {
            Timber.w("Session $id ($packageName) already existed")
            return null
        }
    }

    override fun shouldAcceptSessionDump(id: Int, session: AudioSessionDumpEntry): Boolean {
        if (!session.isUsageRecordable()) {
            Timber.d("Skipped session $id due to usage ($session)")
            return false
        }
        return true
    }
    override fun shouldAddSession(id: Int, uid: Int, packageName: String) = true

    override fun onSessionRemoved(item: IEffectSession) {
        (item as RemoteEffectSession).run {
            item.coroutineContext[Job]!!.invokeOnCompletion {
                item.effect?.close()
            }
            item.coroutineContext[Job]?.cancel()
        }
    }

    fun addSessionByIntent(intent: Intent) {
        val sessionId = intent.getIntExtra(AudioEffect.EXTRA_AUDIO_SESSION, AudioEffect.ERROR)
        if (sessionId < 0 || (app.isLegacyMode && sessionId > 0)) {
            return
        }
        // Legacy mode enabled, remove other sessions
        if (sessionId == 0)
            clearSessions()

        val packageName = intent.getStringExtra(AudioEffect.EXTRA_PACKAGE_NAME) ?: "unknown"

        addSession(
            sessionId,
            context.getUidFromPackage(packageName),
            packageName,
            true
        )
    }

    fun removeSessionByIntent(intent: Intent) {
        val sessionId = intent.getIntExtra(AudioEffect.EXTRA_AUDIO_SESSION, AudioEffect.ERROR)
        val packageName = intent.getStringExtra(AudioEffect.EXTRA_PACKAGE_NAME)
        if (app.isLegacyMode) {
            return
        }

        // Fix: some apps submit sid 0 sending the CLOSE broadcast
        if(packageName != null && sessionId == 0) {
            // Remove by package name instead
            sessionList.filter { it.value.packageName == packageName }
                .keys
                .forEach(::removeSession)
        }
        else {
            removeSession(sessionId)
        }
    }
}

typealias OnRootSessionChangeListener = BaseSessionDatabase.OnSessionChangeListener