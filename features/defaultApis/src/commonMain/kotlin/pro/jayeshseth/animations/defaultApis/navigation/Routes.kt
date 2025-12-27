package pro.jayeshseth.animations.defaultApis.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.core.navigation.RouteSerializer

object DefaultApisRoutes {
    @Serializable
    data object DefaultApisLanding : Route()

    @Serializable
    data object AnimateVisibilityRoute : Route()

    @Serializable
    data object AnimateContentRoute : Route()

    @Serializable
    data object AnimateGestureRoute : Route()

    @Serializable
    data object AnimateValueAsStateRoute : Route()

    @Serializable
    data object InfiniteRotationRoute : Route()

    fun register() {
        RouteSerializer.register<DefaultApisLanding>()
        RouteSerializer.register<AnimateVisibilityRoute>()
        RouteSerializer.register<AnimateContentRoute>()
        RouteSerializer.register<AnimateGestureRoute>()
        RouteSerializer.register<AnimateValueAsStateRoute>()
        RouteSerializer.register<InfiniteRotationRoute>()
    }
}
