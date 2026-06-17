package app.siphondsp.model.rootless

import android.media.audiofx.AudioEffect
import app.siphondsp.model.IEffectSession

data class MutedEffectSession(
    override var uid: Int,
    override var packageName: String,
    var audioMuteEffect: AudioEffect?
) : IEffectSession {
    override fun toString(): String {
        return "package=$packageName; uid=$uid"
    }
}