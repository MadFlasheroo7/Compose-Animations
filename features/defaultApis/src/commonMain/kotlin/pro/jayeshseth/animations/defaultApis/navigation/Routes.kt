package pro.jayeshseth.animations.defaultApis.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.core.navigation.RoutePolymorphicSerializer

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
        RoutePolymorphicSerializer.register<DefaultApisLanding>()
        RoutePolymorphicSerializer.register<AnimateVisibilityRoute>()
        RoutePolymorphicSerializer.register<AnimateContentRoute>()
        RoutePolymorphicSerializer.register<AnimateGestureRoute>()
        RoutePolymorphicSerializer.register<AnimateValueAsStateRoute>()
        RoutePolymorphicSerializer.register<InfiniteRotationRoute>()
    }
}
