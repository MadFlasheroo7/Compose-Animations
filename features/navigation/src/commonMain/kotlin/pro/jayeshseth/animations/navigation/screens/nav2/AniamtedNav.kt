package pro.jayeshseth.animations.navigation.screens.nav2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.ui.components.InteractiveButton

@Composable
fun AnimatedNav(
    hazeState: HazeState,
    scaleNav: () -> Unit,
    slideNav: () -> Unit,
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InteractiveButton(
            hazeState = hazeState,
            text = "Navigate With Scale",
            onClick = scaleNav
        )
        InteractiveButton(
            hazeState = hazeState,
            text = "Navigate With Slide",
            onClick = slideNav
        )
    }
}