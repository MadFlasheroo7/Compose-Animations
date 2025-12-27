package pro.jayeshseth.animations.playground.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.core.navigation.RouteSerializer

object PlaygroundRoutes {
    @Serializable
    data object PlaygroundLandingRoute : Route()

    @Serializable
    data object AnimationSpecRoute : Route()

    @Serializable
    data object TweenAndSpringRoute : Route()

    fun register() {
        RouteSerializer.register<PlaygroundLandingRoute>()
        RouteSerializer.register<AnimationSpecRoute>()
        RouteSerializer.register<TweenAndSpringRoute>()
    }
}
