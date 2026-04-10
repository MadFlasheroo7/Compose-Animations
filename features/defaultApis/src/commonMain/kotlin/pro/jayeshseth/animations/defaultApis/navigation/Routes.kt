package pro.jayeshseth.animations.defaultApis.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.RouteSerializer
import pro.jayeshseth.animations.core.navigation.SecondaryRoute

object DefaultApisRoutes {
    @Serializable
    data object DefaultApisLanding : SecondaryRoute

    @Serializable
    data object AnimateVisibilityRoute : SecondaryRoute

    @Serializable
    data object AnimateContentRoute : SecondaryRoute

    @Serializable
    data object AnimateGestureRoute : SecondaryRoute

    @Serializable
    data object AnimateValueAsStateRoute : SecondaryRoute

    @Serializable
    data object InfiniteRotationRoute : SecondaryRoute

    fun register() {
        RouteSerializer.register<DefaultApisLanding>()
        RouteSerializer.register<AnimateVisibilityRoute>()
        RouteSerializer.register<AnimateContentRoute>()
        RouteSerializer.register<AnimateGestureRoute>()
        RouteSerializer.register<AnimateValueAsStateRoute>()
        RouteSerializer.register<InfiniteRotationRoute>()
    }
}
