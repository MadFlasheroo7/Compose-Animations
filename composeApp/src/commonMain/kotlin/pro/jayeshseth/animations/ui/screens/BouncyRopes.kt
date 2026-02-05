package pro.jayeshseth.animations.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BouncyRopes() {
    var startCoOrdinate by remember {
        mutableStateOf(Offset(0f, 200f))
    }
    var endCoOrdinate by remember {
        mutableStateOf(Offset(300f, 200f))
    }
    val midPoint by remember {
        derivedStateOf {
            val distance = (endCoOrdinate.x - startCoOrdinate.x)
            Offset(
                (endCoOrdinate.x - startCoOrdinate.x) / 2f,
                endCoOrdinate.y + distance
            )
        }
    }
    val path = remember {
        Path()
    }
    val midPointAnimated = animateOffsetAsState(
        midPoint,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessVeryLow
        ), label = "animated mid point"
    )
    val color = MaterialTheme.colorScheme.tertiary
    val handleSize = 30.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        startCoOrdinate = Offset(0f, 200f)
                        endCoOrdinate = Offset(300f, 200f)
                    }
                )
            }
    ) {
        Text(
            text = "Bouncy Rope",
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Canvas(
            modifier = Modifier
                .size(handleSize)
                .offset {
                    IntOffset(
                        (startCoOrdinate.x - handleSize.toPx() / 2).roundToInt(),
                        (startCoOrdinate.y - handleSize.toPx() / 2).roundToInt()
                    )
                }
                .pointerInput("dragStart") {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        startCoOrdinate += dragAmount
                    }
                }) {
            val radius = 15.dp.toPx()
            drawCircle(color, radius = radius)
        }

        Canvas(
            modifier = Modifier
                .size(handleSize)
                .offset {
                    IntOffset(
                        (endCoOrdinate.x - handleSize.toPx() / 2).roundToInt(),
                        (endCoOrdinate.y - handleSize.toPx() / 2).roundToInt()
                    )
                }
                .pointerInput("dragEnd") {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        endCoOrdinate += dragAmount
                    }
                }) {
            val radius = 15.dp.toPx()
            drawCircle(color, radius = radius)
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            path.reset()
            path.moveTo(startCoOrdinate.x, startCoOrdinate.y)
            path.quadraticBezierTo(
                midPointAnimated.value.x,
                midPointAnimated.value.y,
                endCoOrdinate.x,
                endCoOrdinate.y
            )

            drawPath(path, color, style = Stroke(8.dp.toPx()))
        }
    }
}

enum class AnimationStatus {
    Idle,
    Running,
    Finished
}

@Stable
class SvgAnimationState {
    // The internal driver of the animation
    val progress = Animatable(0f)

    // Observable status based on the internal animation state
    val status: AnimationStatus by derivedStateOf {
        when {
            progress.isRunning -> AnimationStatus.Running
            progress.value == 1f -> AnimationStatus.Finished
            else -> AnimationStatus.Idle
        }
    }

    // Function to start the animation
    suspend fun start(
        durationMillis: Int = 2000,
        spec: AnimationSpec<Float> = tween(durationMillis, easing = LinearEasing)
    ) {
        // Reset if already finished
        if (progress.value == 1f) progress.snapTo(0f)

        progress.animateTo(
            targetValue = 1f,
            animationSpec = spec
        )
    }

    // Function to reset
    suspend fun reset() {
        progress.snapTo(0f)
    }
}

@Composable
fun rememberSvgAnimationState() = remember { SvgAnimationState() }

@Composable
fun AnimatedPath(
    pathString: String,
    state: SvgAnimationState,
    modifier: Modifier = Modifier,
    strokeColor: Color = Color.Black,
    strokeWidth: Float = 4f,
    originalViewportSize: Size = Size(24f, 24f) // The viewport size of your original SVG
) {
    // 1. Parse the string into a generic Path object
    val originalPath = remember(pathString) {
        PathParser().parsePathString(pathString).toPath()
    }

    // 2. Prepare objects for the measure logic (reused to avoid allocation)
    val pathMeasure = remember { PathMeasure() }
    val partialPath = remember { Path() }

    // 3. Scale logic (Optional: if you want the path to fill the canvas)
    //    If you strictly want the SVG's original coordinates, you can skip scaling.
    val density = LocalDensity.current

    Canvas(modifier = modifier) {
        val currentProgress = state.progress.value

        // Clear the previous frame's path
        partialPath.reset()

        // 4. Calculate scaling factor to fit the Viewport to the Canvas size
        val scaleX = size.width / originalViewportSize.width
        val scaleY = size.height / originalViewportSize.height
        val scale = minOf(scaleX, scaleY)

        // 5. Setup PathMeasure with the full path
        pathMeasure.setPath(originalPath, forceClosed = false)
        val totalLength = pathMeasure.length

        // 6. Extract the segment based on progress
        //    (startDistance, endDistance, destination, startWithMoveTo)
        pathMeasure.getSegment(
            startDistance = 0f,
            stopDistance = totalLength * currentProgress,
            destination = partialPath,
            startWithMoveTo = true
        )

        // 7. Draw the scaled partial path
        //    We use the drawContext transform to apply scaling easily
        drawContext.canvas.withSave {
            drawContext.canvas.scale(scale, scale)
            drawPath(
                path = partialPath,
                color = strokeColor,
                style = Stroke(
                    width = strokeWidth / scale, // Adjust stroke width to look consistent after scaling
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
    }
}

@Composable
fun BouncyRopew() {
    val scope = rememberCoroutineScope()
    val animState = rememberSvgAnimationState()

    // Example SVG Path (A simple Checkmark)
    // Viewport is assumed to be 24x24 for this path data
//    val checkmarkPath = "M9 16.17 L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z"
    val checkmarkPath = "M15,3C8.373,3 3,8.373 3,15c0,5.623 3.872,10.328 9.092,11.63C12.036,26.468 12,26.28 12,26.047v-2.051c-0.487,0 -1.303,0 -1.508,0c-0.821,0 -1.551,-0.353 -1.905,-1.009c-0.393,-0.729 -0.461,-1.844 -1.435,-2.526c-0.289,-0.227 -0.069,-0.486 0.264,-0.451c0.615,0.174 1.125,0.596 1.605,1.222c0.478,0.627 0.703,0.769 1.596,0.769c0.433,0 1.081,-0.025 1.691,-0.121c0.328,-0.833 0.895,-1.6 1.588,-1.962c-3.996,-0.411 -5.903,-2.399 -5.903,-5.098c0,-1.162 0.495,-2.286 1.336,-3.233C9.053,10.647 8.706,8.73 9.435,8c1.798,0 2.885,1.166 3.146,1.481C13.477,9.174 14.461,9 15.495,9c1.036,0 2.024,0.174 2.922,0.483C18.675,9.17 19.763,8 21.565,8c0.732,0.731 0.381,2.656 0.102,3.594c0.836,0.945 1.328,2.066 1.328,3.226c0,2.697 -1.904,4.684 -5.894,5.097C18.199,20.49 19,22.1 19,23.313v2.734c0,0.104 -0.023,0.179 -0.035,0.268C23.641,24.676 27,20.236 27,15C27,8.373 21.627,3 15,3z"
//    val checkmarkPath =
//                "M45.5,14c-0.259,0 -0.509,0.173 -0.743,0.495c-0.157,0.214 -0.307,0.494 -0.448,0.833c-0.071,0.169 -0.14,0.353 -0.206,0.551c-0.133,0.395 -0.257,0.846 -0.37,1.343c-0.226,0.995 -0.409,2.181 -0.536,3.497c-0.063,0.658 -0.112,1.349 -0.146,2.065C43.017,23.499 43,24.241 43,25s0.017,1.501 0.051,2.217c0.033,0.716 0.082,1.407 0.146,2.065c0.127,1.316 0.31,2.501 0.536,3.497c0.113,0.498 0.237,0.948 0.37,1.343c0.066,0.198 0.135,0.382 0.206,0.551c0.142,0.339 0.292,0.619 0.448,0.833C44.991,35.827 45.241,36 45.5,36c1.381,0 2.5,-4.925 2.5,-11S46.881,14 45.5,14z"

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Status Indicator
        Text(text = "Status: ${animState.status}")
        Text(text = "Progress: ${(animState.progress.value * 100).toInt()}%")

        Spacer(modifier = Modifier.height(24.dp))

        // The Animation Component
        AnimatedPath(
            pathString = checkmarkPath,
            state = animState,
            modifier = Modifier.size(100.dp),
            strokeColor = Color(0xFF4CAF50),
            strokeWidth = 2f,
            originalViewportSize = Size(24f, 24f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Controls
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { scope.launch { animState.start() } },
                enabled = animState.status != AnimationStatus.Running
            ) {
                Text("Animate")
            }

            Button(
                onClick = { scope.launch { animState.reset() } }
            ) {
                Text("Reset")
            }
        }
    }
}