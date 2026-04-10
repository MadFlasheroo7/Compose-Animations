package pro.jayeshseth.animations.ui.composables.wip

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.toPath
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pro.jayeshseth.animations.ui.screens.KfugLogo

enum class AnimationStatus2 { Idle, DrawingStroke, FadingFill, Finished }

@Stable
class SvgAnimationState2 {
    // Separate animatables for stroke (line drawing) and fill (opacity)
    val strokeProgress = Animatable(0f)
    val fillOpacity = Animatable(0f)

    val status: AnimationStatus2 by derivedStateOf {
        when {
            strokeProgress.isRunning -> AnimationStatus2.DrawingStroke
            fillOpacity.isRunning -> AnimationStatus2.FadingFill
            strokeProgress.value >= 1f && fillOpacity.value >= 1f -> AnimationStatus2.Finished
            strokeProgress.value >= 1f -> AnimationStatus2.DrawingStroke // Stroke done, fill pending
            else -> AnimationStatus2.Idle
        }
    }

    suspend fun start(
        strokeDuration: Int = 3000,
        fillDuration: Int = 1000,
        withFill: Boolean = true // Toggle to decide if fill animation runs
    ) {
        // 1. Reset
        reset()

        // 2. Animate Stroke (0 -> 1)
        strokeProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(strokeDuration, easing = LinearEasing)
        )

        // 3. Animate Fill (0 -> 1) ONLY if requested
        if (withFill) {
            fillOpacity.animateTo(
                targetValue = 1f,
                animationSpec = tween(fillDuration, easing = LinearOutSlowInEasing)
            )
        }
    }

    suspend fun reset() {
        strokeProgress.snapTo(0f)
        fillOpacity.snapTo(0f)
    }
}

@Composable
private fun rememberSvgAnimationStates() = remember { SvgAnimationState2() }

@Composable
fun AnimatedVector(
    paths: List<List<PathNode>>,
    state: SvgAnimationState2,
    modifier: Modifier = Modifier,
    originalViewportSize: Size = Size(200f, 200f),
    strokeColor: Color = Color.Black,
    fillColor: Color = Color.Black, // New: The color to fill with
    strokeWidth: Float = 4f,
    drawFill: Boolean = true // New: Toggle to enable/disable filling
) {
    // 1. Pre-calculate Path data (Baked once)
    val pathData = remember(paths) {
        val pMeasure = PathMeasure()
        val totalLengthList = mutableListOf<Float>()
        val pathList = paths.map { nodes ->
            val path = nodes.toPath()
            pMeasure.setPath(path, false)
            totalLengthList.add(pMeasure.length)
            path
        }
        MultiPathData(pathList, totalLengthList, totalLengthList.sum())
    }

    val pathMeasure = remember { PathMeasure() }
    val partialPath = remember { Path() }

    Canvas(modifier = modifier) {
        val strokeProg = state.strokeProgress.value
        val fillAlpha = state.fillOpacity.value

        // Calculate Total Length to draw based on progress
        val currentDrawLength = pathData.totalLength * strokeProg

        // Scale Logic
        val scaleX = size.width / originalViewportSize.width
        val scaleY = size.height / originalViewportSize.height
        val scale = minOf(scaleX, scaleY)

        drawContext.canvas.withSave {
            drawContext.canvas.scale(scale, scale)

            var accumulatedLength = 0f

            // Iterate through layers
            pathData.paths.forEachIndexed { index, path ->
                val pathLength = pathData.lengths[index]
                val startOfPath = accumulatedLength
                val endOfPath = accumulatedLength + pathLength

                // --- A. FILL LOGIC ---
                // We draw the full path fill, but control visibility with Alpha
                if (drawFill && fillAlpha > 0f) {
                    drawPath(
                        path = path,
                        color = fillColor.copy(alpha = fillAlpha), // Animated Alpha
                        style = Fill
                    )
                }

                // --- B. STROKE LOGIC ---
                if (currentDrawLength >= endOfPath) {
                    // Fully drawn path
                    drawPath(
                        path = path,
                        color = strokeColor,
                        style = Stroke(
                            strokeWidth / scale,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                } else if (currentDrawLength > startOfPath) {
                    // Partially drawn path
                    val progressInThisPath = currentDrawLength - startOfPath
                    partialPath.reset()
                    pathMeasure.setPath(path, false)
                    pathMeasure.getSegment(0f, progressInThisPath, partialPath, true)

                    drawPath(
                        path = partialPath,
                        color = strokeColor,
                        style = Stroke(
                            strokeWidth / scale,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }

                accumulatedLength += pathLength
            }
        }
    }
}

// Data holder
private data class MultiPathData(
    val paths: List<Path>,
    val lengths: List<Float>,
    val totalLength: Float
)

@Composable
fun AnimSVGWIP() {
    val scope = rememberCoroutineScope()
    val animState = rememberSvgAnimationStates()

    // Toggle State
    var isFillEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
            .pointerInput(Unit) { // 1. Use pointerInput scope
                awaitPointerEventScope {
                    while (true) {
                        // 2. Await the next event
                        val event = awaitPointerEvent()
                        event.buttons.isPrimaryPressed

                        // 3. Check if it is a Scroll event (Mouse Wheel or Trackpad)
                        if (event.type == PointerEventType.Scroll) {
                            val change = event.changes.first()
                            val delta = change.scrollDelta

                            delta.y

                            // delta.x = Horizontal scroll (Shift+Wheel or Swipe Left/Right)
                            // delta.y = Vertical scroll (Wheel or Swipe Up/Down)

                            println("Scrolled: $delta")

                            // Optional: Consume the event so parents don't scroll
                            change.consume()
                        }
                    }
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // THE ANIMATED LOGO
        AnimatedVector(
            paths = KfugLogo.allLayers, // Using your existing object
            state = animState,
            modifier = Modifier.size(540.dp),
            originalViewportSize = Size(200f, 200f),
            strokeColor = Color(0xFF4285F4),
            fillColor = Color(0xA6C0D6FF), // Same color for fill
            strokeWidth = 6f,
            drawFill = isFillEnabled
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Info Display
        Text("Status: ${animState.status}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Progress: stroke ${(animState.strokeProgress.value * 100).toInt()}%")
        Text(text = "Progress: fill ${(animState.fillOpacity.value * 100).toInt()}%")

        Spacer(modifier = Modifier.height(24.dp))

        // Toggle Switch for Fill
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = isFillEnabled,
                onCheckedChange = { isFillEnabled = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Enable Fill")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                scope.launch {
                    // Pass the toggle state to the start function
                    animState.start(
                        strokeDuration = 3000,
                        withFill = isFillEnabled
                    )
                }
            }) {
                Text("Animate")
            }

            OutlinedButton(onClick = {
                scope.launch { animState.reset() }
            }) {
                Text("Reset")
            }
        }
    }
}