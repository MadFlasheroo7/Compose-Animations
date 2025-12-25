package pro.jayeshseth.animations.core.ui.modifiers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp

/**
 * A `Modifier` that adds a shimmering border effect to a composable.
 *
 * This modifier draws a border around the composable and applies an animated shimmer effect to it.
 * The shimmer is created by a gradient that moves across the border.
 *
 * > NOTE - **not recommended to use it only made for interactive button**
 *
 * @param cornerRadius The shape of the border to be drawn.
 * @param borderWidth The width of the border. Defaults to `4.dp`.
 * @param colors The colors used to create the gradient for the shimmer. Defaults to a list of
 * `Color.Transparent` and `Color.White.copy(alpha = 0.8f)`.
 *
 *
 * @return A `Modifier` instance with the shimmer border effect.
 *```
 * Box(
 *     modifier = Modifier
 *         .size(100.dp)
 *         .shimmerBorder(
 *             cornerRadius = 50.dp,
 *             animatedTranslation = <animatedFloat>,
 *         )
 * )
 * ```
 */
fun Modifier.shimmerBorder(
    cornerRadius: Dp,
    animatedTranslation: Float,
    borderWidth: Float = 4f,
    colors: List<Color> = listOf(
        Color.Transparent,
        Color.White.copy(alpha = 0.8f),
        Color.Transparent
    )
): Modifier = drawBehind {
    val shimmerBrush = Brush.linearGradient(
        colors = colors,
        start = Offset(size.width * animatedTranslation, 0f),
        end = Offset(size.width * (animatedTranslation + 0.3f), size.height)
    )

    drawRoundRect(
        brush = shimmerBrush,
        topLeft = Offset(4f / 2, 4f / 2),
        size = Size(size.width - 4f, size.height - 4f),
        cornerRadius = CornerRadius(cornerRadius.toPx()),
        style = Stroke(width = borderWidth)
    )
}