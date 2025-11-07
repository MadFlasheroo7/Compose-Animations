package pro.jayeshseth.animations.itemPlacements.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.itemPlacements.screens.ItemPlacementAnimation
import pro.jayeshseth.animations.itemPlacements.screens.listItemPlacements.FadeItemPlacement
import pro.jayeshseth.animations.itemPlacements.screens.listItemPlacements.ScaleItemPlacement
import pro.jayeshseth.animations.itemPlacements.screens.listItemPlacements.SlideItemPlacement
import pro.jayeshseth.animations.itemPlacements.screens.listItemPlacements.TrippyBlinders
import pro.jayeshseth.animations.itemPlacements.screens.swipeRefresh.SwipeRefresh

fun EntryProviderBuilder<Route>.itemPlacements(
    onClickLink: OnClickLink,
    onNavAction: OnNavAction
) {
    entry<ItemPlacementRoutes.AnimatedListItemPlacementRoute> {
        ItemPlacementAnimation(onNavAction)
    }
    entry<ItemPlacementRoutes.SlideItemPlacementRoute> {
        SlideItemPlacement(onClickLink)
    }
    entry<ItemPlacementRoutes.ScaleItemPlacementRoute> {
        ScaleItemPlacement(onClickLink)
    }
    entry<ItemPlacementRoutes.FadeItemPlacementRoute> {
        FadeItemPlacement(onClickLink)
    }
    entry<ItemPlacementRoutes.TrippyBlindersRoute> {
        TrippyBlinders()
    }
    entry<ItemPlacementRoutes.SwipeRefreshRoute> {
        SwipeRefresh()
    }
}