package pro.jayeshseth.animations.shaders.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.core.navigation.RouteSerializer
import pro.jayeshseth.animations.core.navigation.SecondaryRoute

object ShaderRoutes {

    @Serializable
    data object ShaderGraphRoute : SecondaryRoute

    @Serializable
    data object RainbowShaderRoute : Route

    @Serializable
    data object InterstellarShaderRoute : Route

    fun register() {
        RouteSerializer.register<ShaderGraphRoute>()
        RouteSerializer.register<RainbowShaderRoute>()
        RouteSerializer.register<InterstellarShaderRoute>()
    }
}
