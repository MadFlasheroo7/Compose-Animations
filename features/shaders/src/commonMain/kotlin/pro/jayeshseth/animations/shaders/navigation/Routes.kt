package pro.jayeshseth.animations.shaders.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route

object ShaderRoutes {

    @Serializable
    data object ShaderGraphRoute : Route()

    @Serializable
    data object RainbowShaderRoute : Route()

    @Serializable
    data object InterstellarShaderRoute : Route()
}
