package pro.jayeshseth.animations.ui.defaultApis.animateValue

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import pro.jayeshseth.animations.util.DURATION

@Composable
fun AnimateFloatAsState(trigger: Boolean) {
    val alpha: Float by animateFloatAsState(
        if (trigger) 1f else 0.5f,
        animationSpec = tween(DURATION, easing = LinearEasing),
        label = "animate float value"
    )

    Box(
        Modifier
            .size(200.dp)
            .graphicsLayer { this.alpha = alpha }
            .background(MaterialTheme.colorScheme.primary)
    )
}
