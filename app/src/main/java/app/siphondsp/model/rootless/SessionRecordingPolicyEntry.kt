package app.siphondsp.model.rootless

data class SessionRecordingPolicyEntry(val uid: Int, val packageName: String, val isRestricted: Boolean)
{
    override fun toString(): String {
        return "uid=$uid; package=$packageName; isRestricted=$isRestricted"
    }
}