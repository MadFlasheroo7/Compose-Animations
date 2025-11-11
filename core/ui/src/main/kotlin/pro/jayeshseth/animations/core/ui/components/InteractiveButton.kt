package pro.jayeshseth.animations.core.ui.components

import android.graphics.BlurMaskFilter
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.BlackCherryCosmos
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.random.Random

@Preview
@Composable
private fun PreviewInteractiveButton() {
    val hazeState = rememberHazeState()
    AnimationsTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.surfaceVariant,
                        )
                    )
                )
//            .hazeSource(state = hazeState),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .hazeSource(state = hazeState)

//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//                            MaterialTheme.colorScheme.primary,
//                            MaterialTheme.colorScheme.surfaceVariant,
//                        )
//                    )
//                )
                    .shaderBackground(BlackCherryCosmos, speed = 0.2f)

            )

//        AnimatedRandomShapesBackground(
////            painter = painterResource(R.drawable.ic_launcher_background),
////            contentDescription = "",
//            modifier = Modifier
//                .fillMaxSize()
//                .hazeSource(state = hazeState),
////            contentScale = ContentScale.Crop
//        )
            InteractiveButton(
                hazeState,
                text = "String", onClick = {
                    println("✨ Animation complete onclick triggred!")
                })
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InteractiveButton(
    hazeState: HazeState,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onLongClick: () -> Unit = {},
    shimmerColor: Color = Color.White,
    backgroundColor: Color = Color(0xFF3B1E1E),
    borderWidth: Float = 4f,
    cornerRadius: Float = 16f,
    height: Dp = 100.dp,
    padding: PaddingValues = PaddingValues(12.dp),
) {
    var clickTracker by remember { mutableIntStateOf(0) }
//    val hazeState = rememberHazeState()
    val cardStyle = HazeStyle(
        backgroundColor = Color.Black,
//        tints = listOf(
//            HazeTint(Color.Cyan.copy(alpha = 0.4f)),
//            HazeTint(Color.Black.copy(alpha = 0.4f))
//        ),
        tint = HazeTint(Color.Transparent),
        blurRadius = 8.dp,
        noiseFactor = HazeDefaults.noiseFactor,
    )
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val buttonInteracted = isPressed.or(isHovered || clickTracker > 0)
    var canExecute by remember { mutableStateOf(false) }

    val animateColor by animateColorAsState(
        targetValue = if (buttonInteracted) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
        animationSpec = tween(500),
        label = "animated button color",
    )
    val animateTextColor by animateColorAsState(
        targetValue = if (buttonInteracted) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onPrimary,
        animationSpec = tween(500),
        label = "animated button color",
    )
    val buttonDp by animateDpAsState(
        targetValue = if (buttonInteracted) 5.dp else 50.dp,
        animationSpec = tween(500),
        label = "animate button shape",
    )

    val shadowColor by animateColorAsState(
        targetValue = if (buttonInteracted) Color.Cyan else Color.Cyan.copy(.20f),
        animationSpec = tween(500),
        label = "animated button color",
    )
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    var moffsetX by remember { mutableStateOf(0f) }
    var moffsetY by remember { mutableStateOf(0f) }
    val spread by remember { mutableStateOf(Animatable(0f)) }

    LaunchedEffect(buttonInteracted, clickTracker) {
        if (buttonInteracted) {
            val spreadJob = async {
                spread.animateTo(
                    targetValue = 10f,
                    animationSpec = keyframes {
                        durationMillis = 500
                        0f at 0 using LinearOutSlowInEasing
                        0f at 200 using FastOutSlowInEasing
                        10f at 500 using FastOutSlowInEasing
                    }
                )
            }

            val offsetXJob = async {
                offsetX.animateTo(
                    targetValue = 0f,
                    animationSpec = keyframes {
                        durationMillis = 500
                        0f at 0 using LinearOutSlowInEasing
                        -10f at 100 using FastOutSlowInEasing
                        -10f at 300 using LinearEasing
                        0f at 500 using FastOutSlowInEasing
                    }
                )
            }

            val offsetYJob = async {
                offsetY.animateTo(
                    targetValue = 0f,
                    animationSpec = keyframes {
                        durationMillis = 500
                        0f at 0 with LinearOutSlowInEasing
                        -10f at 200 with FastOutSlowInEasing
                        -10f at 300 with LinearEasing
                        0f at 500 with FastOutSlowInEasing
                    }
                )
            }

            val results = awaitAll(spreadJob, offsetXJob, offsetYJob)

            val allFinished = results.all { it.endReason == AnimationEndReason.Finished }
            if (allFinished) {
                println("✨ Animation complete!")
                delay(400)
                if (canExecute) onClick()
                if (canExecute) canExecute = false
                clickTracker = 0
            }
        }
    }

//    Surface(
//        shape = RoundedCornerShape(size = buttonDp),
//        color = animateColor,
//        modifier = modifier
//            .height(height)
//            .fillMaxWidth()
//            .padding(padding)
//            .glowingShadow(
//                borderRadius = buttonDp,
//                color = shadowColor,
//                spread = spread.value.dp,
//                blurRadius = 10.dp,
//                offsetX = offsetX.value.dp,
//                offsetY = offsetY.value.dp
//            )
//            .combinedClickable(
//                interactionSource = interactionSource,
//                indication = ripple(),
//                onClick = {
//                    canExecute = true
//                    clickTracker++
//                },
//                onLongClick = {
//                    canExecute = true
//                    clickTracker++
//                }
//            )
//    ) {
//        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(12.dp)) {
//            Text(
//                text = text,
//                color = animateTextColor,
//                style = MaterialTheme.typography.titleMedium,
//            )
//        }
//    }
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "translate"
    )
    Box(
        Modifier
            .fillMaxSize()
//            .hazeSource(state = hazeState)
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        MaterialTheme.colorScheme.primary,
//                        MaterialTheme.colorScheme.surfaceVariant,
//                    )
//                )
//            ),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
//        AnimatedRandomShapesBackground(
////            painter = painterResource(R.drawable.ic_launcher_background),
////            contentDescription = "",
//            modifier = Modifier
//                .fillMaxSize()
////                .hazeSource(state = hazeState),
////            contentScale = ContentScale.Crop
//        ) Icons.Default.Build.root.
    }
    Box(
        Modifier
            .size(200.dp)
            .offset { IntOffset(moffsetX.roundToInt(), moffsetY.roundToInt()) }
            .draggable2D(state = rememberDraggable2DState { delta ->
                moffsetX += delta.x
                moffsetY += delta.y
            })
//            .background(Color.Red)

//                .dropShadow(shape = shape) {
//                    radius = 60f
////                    color = Red500
//                    brush = Brush.radialGradient(
//                        colors = listOf(Yellow200.copy(0.2f), Sky500.copy(.5f))
//                    )
//                }
//            .border(
//                width = 4.dp,
//                shape = RoundedCornerShape(0.dp),
//                brush = Brush.verticalGradient(
//                    colors = listOf(Yellow200, Sky500)
//                ),
//            )
            .hazeEffect(state = hazeState, style = cardStyle)
//            .dropShadow(shape = RoundedCornerShape(0.dp)) {
////                radius = 60f
//                color = Color.Cyan.copy(alpha = .20f)
////                brush = Brush.radialGradient(
////                    colors = listOf(Yellow200.copy(0.2f), Sky500.copy(.5f))
////                )
//
//            }
            .glowingShadow(
//                borderRadius = buttonDp,
                color = shadowColor,
                spread = spread.value.dp,
                blurRadius = 10.dp,
                offsetX = offsetX.value.dp,
                offsetY = offsetY.value.dp
            )
            .combinedClickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = {
                    canExecute = true
                    clickTracker++
                },
                onLongClick = {
                    canExecute = true
                    clickTracker++
                }
            )
            .drawBehind({
                val shimmerBrush = Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        shimmerColor.copy(alpha = 0.8f),
                        Color.Transparent
                    ),
                    start = Offset(size.width * translateAnim, 0f),
                    end = Offset(size.width * (translateAnim + 0.3f), size.height)
                )

                drawRoundRect(
                    brush = shimmerBrush,
                    topLeft = Offset(borderWidth / 2, borderWidth / 2),
                    size = Size(size.width - borderWidth, size.height - borderWidth),
                    cornerRadius = CornerRadius(cornerRadius),
                    style = Stroke(width = borderWidth)
                )
            })
//            .glowingShadow(
//                Color.Cyan.copy(.4f),
//                spread = 10.dp,
//                borderRadius = 11.dp,
//                blurRadius = 10.dp
//            )
    ) {
        Column {

            Text(
                text = text,
                color = Color.Cyan,
                fontWeight = FontWeight.ExtraBold,
//                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

val Sky500 = Color(0xFF03A9F4)
val Yellow200 = Color(0xFFFFEB3B)

private data class MovingShape(
    val id: Int,
    var x: Float,
    var y: Float,
    val color: Color,
    val radius: Float,
    val shapeType: ShapeType,
    var dx: Float, // change in x per frame
    var dy: Float  // change in y per frame
)

private enum class ShapeType {
    CIRCLE, SQUARE, TRIANGLE, STAR // Added STAR
}


@Composable
fun AnimatedRandomShapesBackground(modifier: Modifier = Modifier) {
    val shapes = remember { mutableStateListOf<MovingShape>() }
    val density = androidx.compose.ui.platform.LocalDensity.current.density
    val shapeCount = 15 // Number of shapes

    // Use LaunchedEffect to initialize shapes and run the animation loop
    LaunchedEffect(key1 = Unit) {
        // Initialize shapes
        repeat(shapeCount) { index ->
            shapes.add(
                MovingShape(
                    id = index,
                    x = Random.nextFloat() * 300 * density, // Initial random x
                    y = Random.nextFloat() * 500 * density, // Initial random y
                    color = Color(
                        red = Random.nextFloat(),
                        green = Random.nextFloat(),
                        blue = Random.nextFloat(),
                        alpha = Random.nextFloat().coerceIn(0.5f, 0.9f) // Semi-transparent
                    ),
                    radius = Random.nextInt(15, 40).toFloat() * density,
                    shapeType = ShapeType.values().random(),
                    dx = (Random.nextFloat() - 0.5f) * 2f * density, // Random initial speed and direction
                    dy = (Random.nextFloat() - 0.5f) * 2f * density
                )
            )
        }

        // Animation loop
        while (isActive) {
            withFrameNanos { _ -> // Advance animation frame by frame
                shapes.forEach { shape ->
                    shape.x += shape.dx
                    shape.y += shape.dy

                    // Boundary checks (crude - adjust for canvas size dynamically if needed)
                    // For a more robust solution, get actual canvas size
                    if (shape.x < 0 || shape.x > 400 * density) shape.dx *= -1 // Reverse direction
                    if (shape.y < 0 || shape.y > 600 * density) shape.dy *= -1 // Reverse direction
                }
            }
            delay(16) // Roughly 60 FPS, adjust for slower/faster animation
        }
    }

    // Slowly changing background color
    val animatedBackgroundColor by rememberInfiniteTransition(label = "bgColorTransition").animateColor(
        initialValue = Color(0xFF000000), // Light Gray
        targetValue = Color(0xFF000000), // Light Purple-ish Blue
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing), // Slow transition
            repeatMode = RepeatMode.Reverse
        ),
        label = "bgColor"
    )

    androidx.compose.foundation.Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(color = animatedBackgroundColor) // Draw the animated background color

        shapes.forEach { shape ->
            drawShape(shape)
        }
    }
}

private fun DrawScope.drawShape(shape: MovingShape) {
    when (shape.shapeType) {
        ShapeType.CIRCLE -> {
            drawCircle(
                color = shape.color,
                radius = shape.radius,
                center = Offset(shape.x, shape.y)
            )
        }

        ShapeType.SQUARE -> {
            drawRect(
                color = shape.color,
                topLeft = Offset(shape.x - shape.radius, shape.y - shape.radius),
                size = Size(shape.radius * 2, shape.radius * 2)
            )
        }

        ShapeType.TRIANGLE -> {
            val path = Path().apply {
                moveTo(shape.x, shape.y - shape.radius) // Top point
                lineTo(shape.x - shape.radius, shape.y + shape.radius / 2) // Bottom-left
                lineTo(shape.x + shape.radius, shape.y + shape.radius / 2) // Bottom-right
                close()
            }
            drawPath(path = path, color = shape.color)
        }

        ShapeType.STAR -> {
            val path = createStarPath(shape.x, shape.y, shape.radius, 5, shape.radius / 2)
            drawPath(path = path, color = shape.color)
        }
    }
}

private fun createStarPath(
    centerX: Float,
    centerY: Float,
    outerRadius: Float,
    points: Int,
    innerRadius: Float
): Path {
    val path = Path()
    val angleIncrement = (2 * PI / points).toFloat()
    var currentAngle = (-PI / 2).toFloat() // Start at the top

    path.moveTo(
        centerX + outerRadius * cos(currentAngle),
        centerY + outerRadius * sin(currentAngle)
    )

    for (i in 0 until points) {
        // Point on outer circle
        path.lineTo(
            centerX + outerRadius * cos(currentAngle),
            centerY + outerRadius * sin(currentAngle)
        )
        currentAngle += angleIncrement / 2

        // Point on inner circle
        path.lineTo(
            centerX + innerRadius * cos(currentAngle),
            centerY + innerRadius * sin(currentAngle)
        )
        currentAngle += angleIncrement / 2
    }
    path.close()
    return path
}


/**
 * Draws a **Canvas** behind the modified content giving it a glowing effect
 *
 * @param color color of the glowing shadow
 * @param borderRadius border radius of the glowing shadow
 * @param blurRadius glow radius of the shadow
 * @param offsetX X offset of the shadow
 * @param offsetY Y offset of the shadow
 * @param spread spread radius of the shadow
 */
fun Modifier.glowingShadow(
    color: Color,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    spread: Dp = 0.dp,
): Modifier {
    return this.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }
//            frameworkPaint.color = color.toArgb()
            frameworkPaint.shader = RadialGradientShader(
                center = androidx.compose.ui.geometry.Offset(
                    this.size.width / 2f,
                    this.size.height / 2f
                ),
                radius = this.size.width / 2f,
                colors = listOf(color.copy(alpha = 0f), color)
            )
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint = paint,
            )
        }
    }
}