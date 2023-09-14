package pro.jayeshseth.animations.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pro.jayeshseth.animations.ui.composables.InteractiveButton

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