package pro.jayeshseth.animations.itemPlacements.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.itemPlacements.navigation.ItemPlacementRoutes
import pro.jayeshseth.commoncomponents.InteractiveButton
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedColumn

@Composable
fun ItemPlacementAnimation(
    onNavAction: OnNavAction,
    modifier: Modifier = Modifier
) {
    StatusBarAwareThemedColumn(
        modifier = modifier
            .verticalScroll(
                rememberScrollState()
            )
            .systemBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        InteractiveButton(
            text = "Slide",
            onClick = {
                onNavAction(ItemPlacementRoutes.SlideItemPlacementRoute)
            },
        )
        InteractiveButton(
            text = "Scale",
            onClick = {
                onNavAction(ItemPlacementRoutes.ScaleItemPlacementRoute)
            },
        )
        InteractiveButton(
            text = "Fade",
            onClick = {
                onNavAction(ItemPlacementRoutes.FadeItemPlacementRoute)
            },
        )
//        InteractiveButton(
//            text = "Fidget Toy",
//            onClick = navToTrippyBlinders,
//        )
        InteractiveButton(
            text = "Trippy Blinders",
            onClick = {
                onNavAction(ItemPlacementRoutes.TrippyBlindersRoute)
            },
        )
    }
}