package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.ui.modifiers.shimmerBorder
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationState

/**
 * A Composable that creates an interactive icon button with a "haze" or glassmorphism effect.
 * The button features complex animations on interaction (press, hover, or click), including
 * changes to its shape, shadow, and an inner shadow effect. It also includes a continuous
 * shimmering border animation.
 *
 * The button's shape animates from a rounded rectangle to a circle upon interaction.
 * It leverages the `Haze` library for the glassmorphism effect, blurring content behind it.
 *
 * @param onClick Lambda to be invoked when the button is clicked.
 * @param hazeState The [HazeState] used to apply the blur effect. The content behind this
 *   button should be blurred using a `hazeSource` modifier with the same [HazeState].
 * @param modifier The [Modifier] to be applied to the button.
 * @param hazeStyle The [HazeStyle] to configure the appearance of the haze effect, such as
 *   tint, blur radius, and noise.
 * @param onLongClick Lambda to be invoked for a long-click gesture.
 * @param color The primary color used for the button's animated shadow and effects.
 * @param clickDelay A delay in milliseconds after the click animation finishes before the
 *   internal state is reset.
 * @param contentPadding The padding applied around the icon content.
 * @param icon The composable content to be displayed inside the button, typically an [Icon].
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HazedIconButton(
    onClick: () -> Unit,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    hazeStyle: HazeStyle = cardStyle,
    onLongClick: () -> Unit = {},
    clickDelay: Long = 0,
    contentPadding: PaddingValues = PaddingValues(12.dp),
    icon: @Composable (BoxScope.() -> Unit)
) {
    val customizationState = LocalCustomizationState.current
    val color by animateColorAsState(
        targetValue = Color(customizationState.accentColorArgb),
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    var clickTracker by remember { mutableIntStateOf(0) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val buttonInteracted = isPressed.or(isHovered || clickTracker > 0)
//    val buttonInteracted = true
    var canExecute by remember { mutableStateOf(false) }

    val buttonDp by animateDpAsState(
        targetValue = if (buttonInteracted) 100.dp else 14.dp,
        animationSpec = tween(500),
        label = "animate button shape",
    )

    val shadowColor by animateColorAsState(
        targetValue = if (buttonInteracted) color else color.copy(.40f),
        animationSpec = tween(500),
        label = "animated shadow color",
    )

    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val spread by remember { mutableStateOf(Animatable(0f)) }

    val shape = RoundedCornerShape(buttonDp)

    LaunchedEffect(buttonInteracted) {
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
                delay(clickDelay)
                spread.snapTo(0f)
                offsetX.snapTo(0f)
                offsetY.snapTo(0f)
//                if (canExecute) onClick()
//                if (canExecute) canExecute = false
                clickTracker = 0
            }
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by infiniteTransition.animateFloat(
        initialValue = -4f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, delayMillis = 1000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Restart
        ),
        label = "translate"
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape)
            .hazeEffect(state = hazeState, hazeStyle)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = {
                    onClick()
                    canExecute = true
                    clickTracker++
                },
                onLongClick = {
                    canExecute = true
                    clickTracker++
                }
            )
            .innerShadow(shape) {
                this.color = shadowColor
                this.radius = 10f
                this.spread = 10f
            }
            .shimmerBorder(
                cornerRadius = { buttonDp },
                animatedTranslation = { translateAnim }
            )
    ) {
        CompositionLocalProvider(LocalContentColor provides color) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(contentPadding),
                content = icon
            )
        }
    }
}

@Preview
@Composable
private fun PreviewInteractiveButton() {
    val hazeState = rememberHazeState()
    AnimationsTheme {
        Box(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .hazeSource(state = hazeState)
                    .shaderBackground(InkFlow, speed = 0.2f)
            )
            HazedIconButton(
                onClick = {},
                hazeState = hazeState,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }
}

private val cardStyle = HazeStyle(
    tint = HazeTint(Color.Black.copy(.7f)),
    blurRadius = 80.dp,
    noiseFactor = .5f,
)