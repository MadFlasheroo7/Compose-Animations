package pro.jayeshseth.animations.itemPlacements.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.core.navigation.RouteSerializer

object ItemPlacementRoutes {

    @Serializable
    data object ItemPlacementLandingRoute : Route()

    @Serializable
    data object SwipeRefreshRoute : Route()

    @Serializable
    data object ListItemPlacementRoute : Route()

    @Serializable
    data object FadeItemPlacementRoute : Route()

    @Serializable
    data object ScaleItemPlacementRoute : Route()

    @Serializable
    data object SlideItemPlacementRoute : Route()

    @Serializable
    data object TrippyBlindersRoute : Route()

    fun register() {
        RouteSerializer.register<ItemPlacementLandingRoute>()
        RouteSerializer.register<SwipeRefreshRoute>()
        RouteSerializer.register<ListItemPlacementRoute>()
        RouteSerializer.register<FadeItemPlacementRoute>()
        RouteSerializer.register<ScaleItemPlacementRoute>()
        RouteSerializer.register<SlideItemPlacementRoute>()
        RouteSerializer.register<TrippyBlindersRoute>()
    }
}
