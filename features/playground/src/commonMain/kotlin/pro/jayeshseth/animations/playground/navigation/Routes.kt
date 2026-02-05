package pro.jayeshseth.animations.playground.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.core.navigation.RouteSerializer
import pro.jayeshseth.animations.core.navigation.SecondaryRoute

object PlaygroundRoutes {
    @Serializable
    data object PlaygroundLandingRoute : SecondaryRoute

    @Serializable
    data object AnimationSpecRoute : SecondaryRoute

    @Serializable
    data object TweenAndSpringRoute : Route

    fun register() {
        RouteSerializer.register<PlaygroundLandingRoute>()
        RouteSerializer.register<AnimationSpecRoute>()
        RouteSerializer.register<TweenAndSpringRoute>()
    }
}
