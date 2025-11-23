package pro.jayeshseth.animations.itemPlacements.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.itemPlacements.navigation.ItemPlacementRoutes

@Composable
fun ItemPlacementAnimation(
    hazeState: HazeState,
    onNavAction: OnNavAction,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .verticalScroll(
                rememberScrollState()
            )
            .systemBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        InteractiveButton(
            hazeState = hazeState,
            text = "Slide",
            height = 100.dp,
            onClick = {
                onNavAction(ItemPlacementRoutes.SlideItemPlacementRoute)
            },
        )
        InteractiveButton(
            text = "Scale",
            hazeState = hazeState,
            height = 100.dp,
            onClick = {
                onNavAction(ItemPlacementRoutes.ScaleItemPlacementRoute)
            },
        )
        InteractiveButton(
            text = "Fade",
            hazeState = hazeState,
            height = 100.dp,
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
            hazeState = hazeState,
            height = 100.dp,
            onClick = {
                onNavAction(ItemPlacementRoutes.TrippyBlindersRoute)
            },
        )
    }
}