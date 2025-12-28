package pro.jayeshseth.animations.core.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import org.jetbrains.skia.FilterBlurMode
import org.jetbrains.skia.MaskFilter

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

    drawIntoCanvas { canvas ->
        val paint = Paint()
        val nativePaint = paint.asFrameworkPaint()

        val maxDimension = size.maxDimension
        val scaleFactor = 0.8f

        // Apply blur using Skia's MaskFilter
        if (blurPx > 0) {
            nativePaint.maskFilter = MaskFilter.makeBlur(
                FilterBlurMode.NORMAL,
                blurPx
            )
        }

        // Create radial gradient
        nativePaint.shader = org.jetbrains.skia.Shader.makeRadialGradient(
            x = size.width / 2f + offsetXPx,
            y = size.height / 2f + offsetYPx,
            r = maxDimension * scaleFactor,
            colors = intArrayOf(
                color.copy(alpha = 0.1f).toArgb(),
                color.toArgb()
            )
        )

        canvas.drawRoundRect(
            left = -spreadPx + offsetXPx,
            top = -spreadPx + offsetYPx,
            right = size.width + spreadPx + offsetXPx,
            bottom = size.height + spreadPx + offsetYPx,
            radiusX = borderRadiusPx,
            radiusY = borderRadiusPx,
            paint = paint
        )
    }
}