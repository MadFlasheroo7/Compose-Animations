package pro.jayeshseth.animations.core.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp

actual fun Modifier.glowingShadow(
    color: Color,
    borderRadius: Dp,
    blurRadius: Dp,
    offsetX: Dp,
    offsetY: Dp,
    spread: Dp,
): Modifier = this.drawBehind {
    val spreadPx = spread.toPx()
    val blurPx = blurRadius.toPx()
    val offsetXPx = offsetX.toPx()
    val offsetYPx = offsetY.toPx()
    val borderRadiusPx = borderRadius.toPx()

    val maxDimension = size.maxDimension
    val scaleFactor = 0.8f

    // Approximate blur with multiple layers for web
    val layers = (blurPx / 3).toInt().coerceAtLeast(1).coerceAtMost(10)

    val brush = Brush.radialGradient(
        colors = listOf(
            color.copy(alpha = 0.1f),
            color
        ),
        center = Offset(
            size.width / 2f + offsetXPx,
            size.height / 2f + offsetYPx
        ),
        radius = maxDimension * scaleFactor
    )

    for (i in 0 until layers) {
        val layerAlpha = (1f - (i.toFloat() / layers)) * 0.4f
        drawRoundRect(
            brush = brush,
            topLeft = Offset(
                -spreadPx + offsetXPx - (i * 2),
                -spreadPx + offsetYPx - (i * 2)
            ),
            size = Size(
                size.width + spreadPx * 2 + (i * 4),
                size.height + spreadPx * 2 + (i * 4)
            ),
            cornerRadius = CornerRadius(borderRadiusPx + i),
            alpha = layerAlpha
        )
    }
}