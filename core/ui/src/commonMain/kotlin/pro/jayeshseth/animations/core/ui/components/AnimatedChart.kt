package pro.jayeshseth.animations.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/**
 * A composable that renders an animated line chart.
 *
 * The chart path is drawn based on a list of points and a progress value,
 * which animates the drawing of the path from start to finish. It also includes
 * two horizontal dashed grid lines at y=0.0 and y=1.0, and a "head" circle
 * at the current end of the animated path.
 *
 * The coordinate system for the input points is normalized, where the X-axis
 * is expected to be between 0f and 1f. The Y-axis is automatically scaled
 * to fit the provided points, while ensuring the 0f to 1f range is visible.
 *
 * @param points A list of [Offset] points to draw the chart line. The x-values should be
 *   normalized between 0.0 and 1.0.
 * @param progress A float value between 0.0 and 1.0 indicating the progress of the
 *   path drawing animation. 0.0 means no path is drawn, 1.0 means the full path is drawn.
 * @param modifier The [Modifier] to be applied to the chart container.
 * @param gridColor The [Color] of the horizontal grid lines.
 * @param pathColor The [Color] of the chart path and the head circle.
 */
@Composable
fun AnimatedChart(
    points: List<Offset>,
    progress: Float,
    modifier: Modifier = Modifier,
    gridColor: Color = MaterialTheme.colorScheme.tertiary,
    pathColor: Color = MaterialTheme.colorScheme.tertiary
) {
    Box(
        modifier = modifier
            .drawWithCache {
                val minX = 0f
                val maxX = 1f
                val minY = minOf(0f, points.minOfOrNull { it.y } ?: 0f)
                val maxY = maxOf(1f, points.maxOfOrNull { it.y } ?: 1f)

                val padding = 20.dp.toPx()

                // Coordinate transformation
                fun Float.toCanvasX() =
                    padding + (this - minX) / (maxX - minX) * (size.width - 2 * padding)

                fun Float.toCanvasY() =
                    size.height - padding - (this - minY) / (maxY - minY) * (size.height - 2 * padding)

                val gridPathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)

                // Draw path
                val path = Path()
                path.moveTo(points.first().x.toCanvasX(), points.first().y.toCanvasY())
                points.forEach { path.lineTo(it.x.toCanvasX(), it.y.toCanvasY()) }

                // Animate path drawing
                val pathMeasure =
                    PathMeasure().apply { setPath(path, false) }
                val animatedPath = Path()
                pathMeasure.getSegment(0f, pathMeasure.length * progress, animatedPath, true)

                onDrawBehind {
                    // Horizontal line for y=1.0 and y=0.0
                    drawLine(
                        gridColor,
                        start = Offset(0f.toCanvasX(), 1f.toCanvasY()),
                        end = Offset(1f.toCanvasX(), 1f.toCanvasY()),
                        pathEffect = gridPathEffect
                    )
                    drawLine(
                        gridColor,
                        start = Offset(0f.toCanvasX(), 0f.toCanvasY()),
                        end = Offset(1f.toCanvasX(), 0f.toCanvasY()),
                        pathEffect = gridPathEffect
                    )

                    drawPath(animatedPath, pathColor, style = Stroke(width = 3.dp.toPx()))
                    // head circle
                    if (progress > 0f) {
                        val pos = pathMeasure.getPosition(pathMeasure.length * progress)
                        drawCircle(pathColor, radius = 6.dp.toPx(), center = pos)
                    }
                }
            }
    )
}