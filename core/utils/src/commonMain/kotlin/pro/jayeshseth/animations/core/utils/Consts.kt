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

object DefaultPrefKeys {
    // Pref Keys
    const val BACKGROUND_BLUR_KEY = "background_blur"
    const val PRIMARY_COLOR_KEY = "primary_color"
    const val HEADING_COLOR_KEY = "heading_color"
    const val ACCENT_COLOR_KEY = "accent_color"
    const val PRIMARY_BUTTON_ACCENT_COLOR_KEY = "primary_button_accent_color"
    const val BUTTON_ACCENT_COLOR_KEY = "button_accent_color"
    const val SHADER_KEY = "shader_key"
    const val SHADER_SPEED_KEY = "shader_speed"
    const val BACKGROUND_TYPE_KEY = "background_type"
    const val CANVAS_KEY = "canvas_key"
    const val CANVAS_COLOR_KEY = "canvas_color"
    const val IMAGE_KEY = "image_key"

    // Shader Content Keys
    const val INK_FLOW_KEY = "ink_flow"
    const val BLACK_CHERRY_COSMOS_KEY = "black_cherry_cosmos"
    const val OIL_FLOW_KEY = "oil_flow"
    const val HEAT_KEY = "heat"
    const val GOLDEN_MAGMA_KEY = "golden_magma"
    const val BUBBLE_RINGS_KEY = "bubble_rings"
    const val STAGE_KEY = "stage"
    const val ICE_REFLECTION_KEY = "ice_reflection"
    const val GRADIENT_FLOW_KEY = "gradient_flow"
    const val PURPLE_LIQUID_KEY = "purple_liquid"
    const val STRIPY_KEY = "stripy"
    const val AURORA_KEY = "aurora"

    // Canvas Content Keys
    const val DOT_GRID_KEY = "dot_grid"

    // Image Content Keys
    const val CAT_IMAGE_KEY = "cat"
}