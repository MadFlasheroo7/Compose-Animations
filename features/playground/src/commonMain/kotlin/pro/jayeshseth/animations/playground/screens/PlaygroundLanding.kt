package pro.jayeshseth.animations.playground.screens

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
import pro.jayeshseth.animations.playground.navigation.PlaygroundRoutes

@Composable
fun PlaygroundLanding(
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    navAction: OnNavAction
) {
    Column(
        modifier = modifier
            .verticalScroll(
                rememberScrollState()
            )
            .systemBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        InteractiveButton(
            hazeState = hazeState,
            text = "Animation Specs",
            height = 100.dp,
            onClick = { navAction(PlaygroundRoutes.AnimationSpecRoute) },
        )
    }
}