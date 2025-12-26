package pro.jayeshseth.animations.itemPlacements.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route

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
}
