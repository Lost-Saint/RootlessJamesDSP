package app.siphondsp.session.dump.data

import app.siphondsp.model.AudioSessionDumpEntry

interface ISessionInfoDump : IDump {
    val sessions: HashMap<Int /* sid */, AudioSessionDumpEntry>
}