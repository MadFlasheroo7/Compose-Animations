package pro.jayeshseth.animations.ui.composables

import android.graphics.BlurMaskFilter
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import pro.jayeshseth.animations.core.ui.theme.syneFontFamily

@Preview
@Composable
private fun PreviewInteractiveButton() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        InteractiveButton(text = "String", onClick = {
            println("✨ Animation complete onclick triggred!")
        })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InteractiveButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onLongClick: () -> Unit = {},
    height: Dp = 100.dp,
    padding: PaddingValues = PaddingValues(12.dp),
) {
    var clickTracker by remember { mutableIntStateOf(0) }

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
        targetValue = if (buttonInteracted) MaterialTheme.colorScheme.tertiary else Color.Transparent,
        animationSpec = tween(500),
        label = "animated button color",
    )
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

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

    Surface(
        shape = RoundedCornerShape(size = buttonDp),
        color = animateColor,
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .padding(padding)
            .glowingShadow(
                borderRadius = buttonDp,
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
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(12.dp)) {
            Text(
                text = text,
                color = animateTextColor,
                fontFamily = syneFontFamily,
                style = MaterialTheme.typography.titleMedium,
            )
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