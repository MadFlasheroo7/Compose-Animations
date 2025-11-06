package pro.jayeshseth.animations.navigation.screens.nav2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pro.jayeshseth.commoncomponents.InteractiveButton

@Composable
fun AnimatedNav(
    scaleNav: () -> Unit,
    slideNav: () -> Unit,
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InteractiveButton(
            text = "Navigate With Scale",
            onClick = scaleNav
        )
        InteractiveButton(
            text = "Navigate With Slide",
            onClick = slideNav
        )
    }
}