package app.siphondsp.session.dump.data

interface ISessionPolicyInfoDump : IDump {
    val capturePermissionLog: HashMap<String /* package */, Boolean /* captureAllowed */>
}