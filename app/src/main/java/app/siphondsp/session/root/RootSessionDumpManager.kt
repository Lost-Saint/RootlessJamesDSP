package app.siphondsp.session.root

import android.content.*
import app.siphondsp.session.dump.DumpManager
import app.siphondsp.session.dump.data.ISessionInfoDump
import app.siphondsp.session.shared.BaseSessionManager


class RootSessionDumpManager(context: Context) : BaseSessionManager(context)
{
    private var onSessionDump: ((sessionDump: ISessionInfoDump) -> Unit)? = null
    private var onDumpMethodChanged: (() -> Unit)? = null

    fun setOnSessionDump(callback: ((sessionDump: ISessionInfoDump) -> Unit)?) {
        onSessionDump = callback
    }

    fun setOnDumpMethodChanged(callback: (() -> Unit)?) {
        onDumpMethodChanged = callback
    }

    override fun handleSessionDump(sessionDump: ISessionInfoDump?) {
        sessionDump?.let { onSessionDump?.invoke(it) }
    }

    override fun onDumpMethodChange(method: DumpManager.Method) {
        onDumpMethodChanged?.invoke()
        super.onDumpMethodChange(method)
    }
}