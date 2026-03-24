package pro.jayeshseth.animations.core.ui.backgrounds.canvas

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.utils.DefaultPrefKeys
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * A canvas background that draws an animated grid of dots.
 *
 * Dots are spaced 32 px apart on a dark (#121212) background. A sinusoidal wave
 * modulates each dot's radius and opacity based on its distance from center,
 * creating a pulsing ripple effect over a 4-second cycle.
 *
 * @param colorArgb ARGB color used to tint the dots.
 */
data class DotGridCanvas(
    override val colorArgb: Int = 0xFF00FFFF.toInt()
) : BackgroundType.Canvas {
    override val key = DefaultPrefKeys.DOT_GRID_KEY
    override val displayName = "Dot Grid"
    override val previewColors = listOf(0xFF121212.toInt(), 0xFF00FFFF.toInt(), 0xFF121212.toInt())
    override fun withColor(colorArgb: Int) = copy(colorArgb = colorArgb)

    @Composable
    override fun Content(modifier: Modifier) {
        DotGridPattern(Color(colorArgb), modifier)
    }
}

/**
 * Draws an animated grid of circles with wave-modulated size and opacity.
 */
@Preview
@Composable
private fun DotGridPattern(
    dotColor: Color = Color.Cyan,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val spacing = 32f
        val baseRadius = 4f
        drawRect(Color(0xFF121212))

        var x = spacing / 2
        while (x < size.width) {
            var y = spacing / 2
            while (y < size.height) {
                val distX = (x - center.x) / size.width
                val distY = (y - center.y) / size.height
                val wave = sin(phase + distX * 6f) * cos(phase + distY * 6f)
                val radius = baseRadius + baseRadius * wave * 0.6f
                drawCircle(
                    color = dotColor.copy(alpha = 0.5f + 0.5f * wave),
                    radius = radius,
                    center = Offset(x, y)
                )
                y += spacing
            }
            x += spacing
        }
    }
}
