package pro.jayeshseth.animations.itemPlacements.navigation

import androidx.navigation3.runtime.EntryProviderScope
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.itemPlacements.screens.ItemPlacementAnimation
import pro.jayeshseth.animations.itemPlacements.screens.listItemPlacements.FadeItemPlacement
import pro.jayeshseth.animations.itemPlacements.screens.listItemPlacements.ScaleItemPlacement
import pro.jayeshseth.animations.itemPlacements.screens.listItemPlacements.SlideItemPlacement
import pro.jayeshseth.animations.itemPlacements.screens.listItemPlacements.TrippyBlinders
import pro.jayeshseth.animations.itemPlacements.screens.swipeRefresh.SwipeRefresh

fun EntryProviderScope<Route>.itemPlacements(
    hazeState: HazeState,
    onClickLink: OnClickLink,
    onNavAction: OnNavAction
) {
    entry<ItemPlacementRoutes.ListItemPlacementRoute> {
        ItemPlacementAnimation(
            hazeState,
            onNavAction
        )
    }
    entry<ItemPlacementRoutes.SlideItemPlacementRoute> {
        SlideItemPlacement(
            hazeState,
            onClickLink
        )
    }
    entry<ItemPlacementRoutes.ScaleItemPlacementRoute> {
        ScaleItemPlacement(
            hazeState,
            onClickLink
        )
    }
    entry<ItemPlacementRoutes.FadeItemPlacementRoute> {
        FadeItemPlacement(
            hazeState,
            onClickLink
        )
    }
    entry<ItemPlacementRoutes.TrippyBlindersRoute> {
        TrippyBlinders()
    }
    entry<ItemPlacementRoutes.SwipeRefreshRoute> {
        SwipeRefresh()
    }
}