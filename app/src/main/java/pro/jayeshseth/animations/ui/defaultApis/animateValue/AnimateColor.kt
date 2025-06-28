package pro.jayeshseth.animations.ui.defaultApis.animateValue

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AnimateColorAsState(trigger: Boolean) {
    val triggered = rememberUpdatedState(trigger)
    val color by animateColorAsState(
        if (triggered.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary,
        animationSpec = tween(1000, easing = LinearEasing), label = "animate color"
    )
    Box(
        Modifier
            .size(200.dp)
            .background(color)
    )
}