package pro.jayeshseth.animations.core.utils

// TODO
val isDebug = true
//val isDebug = BuildConfig.DEBUG

val branch = if (isDebug) "beta" else "main"

val BASE_URL =
    "https://github.com/MadFlasheroo7/Compose-Animations/tree/$branch"

const val DURATION = 1000
const val CAN_PLAY_SHADER = "can_play_shader_key"

object PermissionPrefs {
    const val RECORD_AUDIO_DENIED = "record_audio_denied"
    const val PERMISSION_RATIONAL = "record_audio_rational"
}