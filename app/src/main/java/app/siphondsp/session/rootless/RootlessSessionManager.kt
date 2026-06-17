package app.siphondsp.session.rootless

import android.content.*
import app.siphondsp.session.dump.DumpManager
import app.siphondsp.session.dump.data.ISessionInfoDump
import app.siphondsp.session.dump.data.ISessionPolicyInfoDump
import app.siphondsp.session.shared.BaseSessionManager


class RootlessSessionManager(context: Context) : BaseSessionManager(context)
{
    // Session database
    val sessionDatabase: RootlessSessionDatabase = RootlessSessionDatabase(context)
    // Session policy database
    val sessionPolicyDatabase: SessionRecordingPolicyManager = SessionRecordingPolicyManager(context)

    override fun destroy()
    {
        super.destroy()
        sessionDatabase.destroy()
        sessionPolicyDatabase.destroy()
    }

    override fun handleSessionDump(sessionDump: ISessionInfoDump?) {
        if(sessionDump is ISessionPolicyInfoDump) {
            sessionPolicyDatabase.update(sessionDump)
        }
        else {
            dumpManager.dumpCaptureAllowlistLog()?.let { sessionPolicyDatabase.update(it) }
        }

        sessionDump?.let { sessionDatabase.update(it) }
    }

    override fun onDumpMethodChange(method: DumpManager.Method) {
        sessionDatabase.clearSessions()
        sessionPolicyDatabase.clearSessions()
        super.onDumpMethodChange(method)
    }
}