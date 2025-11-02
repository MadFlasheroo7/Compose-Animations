package pro.jayeshseth.animations.defaultApis.animations.animateValue

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
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
import pro.jayeshseth.animations.core.model.DURATION

@Composable
fun AnimateDpAsState(trigger: Boolean) {
    val triggered = rememberUpdatedState(trigger)
    val size by animateDpAsState(
        if (triggered.value) 200.dp else 100.dp,
        animationSpec = tween(DURATION, easing = LinearEasing),
        label = "animate dp"
    )
    Box(
        Modifier
            .size(size)
            .background(MaterialTheme.colorScheme.primary)
    )
}