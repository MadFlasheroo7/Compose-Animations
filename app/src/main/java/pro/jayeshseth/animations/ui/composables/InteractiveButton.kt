package pro.jayeshseth.animations.ui.composables

import android.graphics.BlurMaskFilter
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Preview
@Composable
private fun InteractiveButton() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        InteractiveButtonA(text = "String", onClick = {})
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InteractiveButtonA(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onLongClick: () -> Unit = {},
    height: Dp = 100.dp,
    padding: PaddingValues = PaddingValues(12.dp),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val buttonInteracted = isPressed.or(isHovered)

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
        targetValue = if (buttonInteracted) 5.dp else 100.dp,
        animationSpec = tween(500),
        label = "animate button shape",
    )
    // --- Animation for oscillating offset ---
    val infiniteTransition = rememberInfiniteTransition(label = "infinite offset transition")

    val oscillatingOffset by infiniteTransition.animateValue(
        initialValue = 10.dp,
        targetValue = -10.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 700,
                easing = LinearEasing
            ), // Adjust duration/easing
            repeatMode = RepeatMode.Reverse
        ),
        label = "oscillating offset"
    )

    // Use derivedStateOf to choose between oscillating offset and 0.dp
    // This ensures that when buttonInteracted becomes false, the offset smoothly animates to 0.dp
    val offset by animateDpAsState(
        targetValue = if (buttonInteracted) oscillatingOffset else 0.dp,
        animationSpec = tween(durationMillis = 300), // Shorter duration for snapping back to 0 or starting oscillation
        label = "offset decision"
    )
//    val offset by animateDpAsState(
//        targetValue = if (buttonInteracted) -10.dp else 0.dp,
//        animationSpec = tween(500),
//        label = "animate button shape",
//    )
    val shadowColor by animateColorAsState(
        targetValue = if (buttonInteracted) Color.Cyan else Color.Transparent,
        animationSpec = tween(500),
        label = "animated button color",
    )
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    val spread by remember { mutableStateOf(Animatable(0f)) }

    /***
     * initial
     * spread - 0.dp
     * x- -10
     * y- -10
     *
     * once y is -10
     * spread - 10
     * x & y - 10
     *
     */
    var text by remember { mutableStateOf("string") }
    LaunchedEffect(buttonInteracted) {
        if (buttonInteracted) {
            launch {
                spread.animateTo(
                    targetValue = 10f,
                    animationSpec = keyframes {
                        durationMillis = 500
                        0f at 0 with LinearOutSlowInEasing
                        0f at 200 with FastOutSlowInEasing
                        10f at 500 with FastOutSlowInEasing
                    }
                )
            }
            launch {
                offsetX.animateTo(
                    targetValue = 0f,
                    animationSpec = keyframes {
                        durationMillis = 500
                        0f at 0 with LinearOutSlowInEasing
                        -10f at 100 with FastOutSlowInEasing
                        -10f at 300 with LinearEasing
                        0f at 500 with FastOutSlowInEasing
                    }
                )
            }
            launch {
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
        }
    }

    LaunchedEffect(buttonInteracted) {
        if (buttonInteracted) {

        }
    }

    Surface(
        shape = RoundedCornerShape(size = buttonDp),
        color = animateColor,
        modifier = modifier
            .height(height)
            .padding(padding)
//            .dropShadow(
//                RoundedCornerShape(buttonDp),
//                shadow = Shadow(
//                    radius = 12.dp,
////                    color = Color.Cyan,
//                    brush = Brush.sweepGradient(
//                        colorStops = arrayOf(
//                            0.0f to shadowColor,
////                            0.5f to Color.Transparent,
////                            0.5f to Color.Transparent,
//                            1.0f to shadowColor
//                        ),
////                        center = Offset(
////                            x = 0f,
////                            y = 120f
////                        ),
////                        colors = listOf(
////                            Color.Blue,
////                            Color.Cyan
////                        )
//                    ),
//                    spread = 10.dp,
////                    offset = DpOffset(-10.dp, 0.dp)
//                )
//            )

            .glowingShadow(
                borderRadius = buttonDp,
                color = shadowColor,
                spread = spread.value.dp,
//                blurRadius = 5.dp,
                blurRadius = 10.dp,
                offsetX = offsetX.value.dp,
                offsetY = offsetY.value.dp
            )
            /***
             * initial
             * spread - 0.dp
             * x- -10
             * y- -10
             *
             * once y is -10
             * spread - 10
             * x & y - 10
             */
            .combinedClickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = { onClick() },
                onLongClick = onLongClick
            )
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(12.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = animateTextColor
                )
                Text(
                    text = text,
                    color = animateTextColor,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
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
            frameworkPaint.color = color.toArgb()
//            frameworkPaint.shader = RadialGradientShader(
//                center = androidx.compose.ui.geometry.Offset(this.size.width / 2f, this.size.height / 2f),
//                radius = this.size.width / 2f,
//                colors = listOf(color.copy(alpha = 0f), color)
//            )
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

@Composable
fun SweepGradientReveal(
    isRevealing: Boolean,
    modifier: Modifier = Modifier,
    animationType: SweepAnimationType = SweepAnimationType.CLOCKWISE,
    colors: List<Color> = listOf(
        Color(0xFF6366F1), // Indigo
        Color(0xFF8B5CF6), // Violet
        Color(0xFFEC4899), // Pink
        Color(0xFFF59E0B), // Amber
        Color(0xFF10B981)  // Emerald
    ),
    content: @Composable BoxScope.() -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sweep_transition")

    // Base rotation animation
    val animatedAngle by animateFloatAsState(
        targetValue = if (isRevealing) {
            when (animationType) {
                SweepAnimationType.CLOCKWISE -> 360f
                SweepAnimationType.COUNTER_CLOCKWISE -> -360f
                SweepAnimationType.PULSE -> 0f
                SweepAnimationType.WAVE -> 720f
                SweepAnimationType.CONTINUOUS ->0f
            }
        } else 0f,
        animationSpec = when (animationType) {
            SweepAnimationType.WAVE -> spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )

            SweepAnimationType.PULSE -> spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessMedium
            )

            else -> tween(
                durationMillis = 1500,
                easing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)
            )
        },
        label = "sweep_angle"
    )

    // Continuous rotation for infinite effect
    val continuousRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "continuous_rotation"
    )

    // Scale animation for pulse effect
    val scale by animateFloatAsState(
        targetValue = if (isRevealing && animationType == SweepAnimationType.PULSE) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )

    // Alpha animation for fade effect
    val alpha by animateFloatAsState(
        targetValue = if (isRevealing) 1f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                    rotationZ = when (animationType) {
                        SweepAnimationType.CONTINUOUS -> continuousRotation
                        else -> animatedAngle
                    }
                }
        ) {
            drawSweepGradientReveal(
                colors = colors,
                animationType = animationType,
                progress = if (isRevealing) 1f else 0f,
                angle = animatedAngle
            )
        }

        content()
    }
}

enum class SweepAnimationType {
    CLOCKWISE,
    COUNTER_CLOCKWISE,
    PULSE,
    WAVE,
    CONTINUOUS
}

private fun DrawScope.drawSweepGradientReveal(
    colors: List<Color>,
    animationType: SweepAnimationType,
    progress: Float,
    angle: Float
) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = minOf(size.width, size.height) / 2

    when (animationType) {
        SweepAnimationType.CLOCKWISE, SweepAnimationType.COUNTER_CLOCKWISE -> {
            val sweepBrush = Brush.sweepGradient(
                colors = colors,
                center = center
            )
            drawCircle(
                brush = sweepBrush,
                radius = radius,
                center = center,
                alpha = progress
            )
        }

        SweepAnimationType.PULSE -> {
            val pulseBrush = Brush.sweepGradient(
                colors = colors.map { it.copy(alpha = progress) },
                center = center
            )
            drawCircle(
                brush = pulseBrush,
                radius = radius * progress,
                center = center
            )
        }

        SweepAnimationType.WAVE -> {
            // Create wave effect with multiple gradients
            val segments = 3
            for (i in 0 until segments) {
                val segmentAngle = (360f / segments) * i + angle
                val segmentBrush = Brush.sweepGradient(
                    colors = colors,
                    center = center
                )
                drawArc(
                    brush = segmentBrush,
                    startAngle = segmentAngle,
                    sweepAngle = 120f * progress,
                    useCenter = true,
                    size = Size(radius * 2, radius * 2),
                    topLeft = Offset(center.x - radius, center.y - radius),
                    alpha = 0.7f
                )
            }
        }

        SweepAnimationType.CONTINUOUS -> {
            val continuousBrush = Brush.sweepGradient(
                colors = colors,
                center = center
            )
            drawCircle(
                brush = continuousBrush,
                radius = radius,
                center = center
            )
        }
    }
}

@Composable
fun SweepGradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isRevealing: Boolean = false
) {
    SweepGradientReveal(
        isRevealing = isRevealing,
        modifier = modifier
            .size(120.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        animationType = SweepAnimationType.CLOCKWISE
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.9f)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun SweepGradientCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    isRevealing: Boolean = false
) {
    SweepGradientReveal(
        isRevealing = isRevealing,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp)),
        animationType = SweepAnimationType.WAVE
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.95f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SweepGradientDemo() {
    var isRevealing by remember { mutableStateOf(false) }
    var selectedAnimation by remember { mutableStateOf(SweepAnimationType.CLOCKWISE) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF0F172A) // Dark slate background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sweep Gradient Reveal",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                // Animation type selector
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(SweepAnimationType.values()) { type ->
                        FilterChip(
                            onClick = { selectedAnimation = type },
                            label = {
                                Text(
                                    text = type.name.replace("_", " "),
                                    fontSize = 10.sp
                                )
                            },
                            selected = selectedAnimation == type,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF6366F1),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                // Main demonstration
                SweepGradientReveal(
                    isRevealing = isRevealing,
                    animationType = selectedAnimation,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.9f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "✨",
                                fontSize = 32.sp
                            )
                            Text(
                                text = "Reveal",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }

                // Control buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { isRevealing = !isRevealing },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6366F1)
                        )
                    ) {
                        Text(if (isRevealing) "Hide" else "Reveal")
                    }

                    OutlinedButton(
                        onClick = { isRevealing = false },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Text("Reset")
                    }
                }

                // Example implementations
                SweepGradientButton(
                    text = "Click Me",
                    onClick = { isRevealing = !isRevealing },
                    isRevealing = isRevealing
                )

                SweepGradientCard(
                    title = "Gradient Card",
                    description = "This card showcases the wave animation effect with smooth sweep gradients.",
                    isRevealing = isRevealing
                )
            }
        }
    }
}