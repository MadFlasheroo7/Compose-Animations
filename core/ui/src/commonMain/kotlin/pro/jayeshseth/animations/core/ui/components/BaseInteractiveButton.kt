package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pro.jayeshseth.animations.core.ui.modifiers.glowingShadow
import pro.jayeshseth.animations.core.ui.modifiers.shimmerBorder

// TODO make is accessible and testable
/**
 * A highly customizable interactive button with advanced visual effects including haze (glassmorphism),
 * glowing shadows, inner shadows, and shimmer borders.
 *
 * This button features animated transitions for hover, press, and click states, including
 * shape morphing and glowing spread animations.
 *
 * @param hazeState The [HazeState] used to apply the glassmorphism effect.
 * @param modifier The [Modifier] to be applied to the button.
 * @param hazeStyle The [HazeStyle] to define the appearance of the haze effect.
 * @param onLongClick Callback to be invoked when the button is long-pressed.
 * @param clickDelay The delay in milliseconds after the click animation starts before [onClick] is invoked.
 * @param color The primary theme color used for shadows, glows, and overlays.
 * @param flip If true, reverses the shape morphing logic (e.g., morphing from rounded to pill shape).
 * @param scale The scale factor applied to the entire button.
 * @param blur The blur radius applied to the button's graphics layer.
 * @param showOverlay Whether to draw a radial gradient overlay behind the content.
 * @param onClick Callback to be invoked when the button is clicked.
 * @param content The composable content to be displayed inside the button.
 */
@Composable
fun BaseInteractiveButton(
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    hazeStyle: HazeStyle = HazeStyle.Unspecified,
    onLongClick: () -> Unit = {},
    clickDelay: Long = 0L,
    color: Color = Color.Cyan,
    flip: Boolean = false,
    scale: Float = 1f,
    blur: Float = 0f,
    showOverlay: Boolean = false,
    onClick: () -> Unit,
    content: @Composable (BoxScope.() -> Unit)
) {
    val scope = rememberCoroutineScope()

    val spread = remember { Animatable(0f) }
    val radius = remember { Animatable(0f) }
    val spread2 = remember { Animatable(1500f) }

    var isPressed by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }
    var isAnimatingClick by remember { mutableStateOf(false) }

    val isVisuallyActive = isPressed || isAnimatingClick || isHovered

    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val transition = updateTransition(isVisuallyActive, "button animation")

    val buttonDp by transition.animateDp(
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow,
            )
        }
    ) { if (it xor flip) 100.dp else 20.dp }

    val shadowColor by transition.animateColor(
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow,
            )
        }
    ) { if (it) color else color.copy(alpha = .4f) }

    val shape by remember(buttonDp, flip) { mutableStateOf(RoundedCornerShape(buttonDp)) }

    // Infinite Shimmer anim
    val translateAnim by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5500, easing = EaseInCubic),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    // Temp inner radius, a workaround for inner shadow bug
    val innerRadius by infiniteTransition.animateFloat(
        initialValue = 4.9f,
        targetValue = 5f,
        label = "",
        animationSpec = infiniteRepeatable(
            animation = tween(5000),
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        content = content,
        modifier = modifier
            .fillMaxWidth()
            .glowingShadow(
                borderRadius = buttonDp,
                color = color,
                spread = spread.value.dp,
                blurRadius = radius.value.dp
            )
            .clip(shape)
            .hazeEffect(state = hazeState, style = hazeStyle)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Enter -> isHovered = true
                            PointerEventType.Exit -> isHovered = false
                        }
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongClick() },
                    onPress = {
                        isPressed = true

                        val isReleased = tryAwaitRelease()

                        isPressed = false

                        if (isReleased) {
                            isAnimatingClick = true

                            /**
                             * runs the animation concurrently and handles the cancellation
                             */
                            scope.launch {
                                // Run the outward spread animations concurrently
                                awaitAll(
                                    async { radius.animateTo(20f, tween(500)) },
                                    async { spread.animateTo(10f, tween(500)) },
                                    async { spread2.animateTo(400f, tween(500)) }
                                )

                                delay(clickDelay)
                                onClick()

                                awaitAll(
                                    async { radius.animateTo(0f, tween(300)) },
                                    async { spread.animateTo(0f, tween(300)) },
                                    async { spread2.animateTo(1500f, tween(300)) }
                                )

                                isAnimatingClick = false
                            }
                        } else {
                            scope.launch {
                                awaitAll(
                                    async { radius.animateTo(0f, tween(300)) },
                                    async { spread.animateTo(0f, tween(300)) },
                                    async { spread2.animateTo(1500f, tween(300)) }
                                )
                            }
                        }
                    }
                )
            }
            // TODO migrate glowing shadow and replace this
            .then(
                if (showOverlay) {
                    Modifier.drawBehind {
                        drawIntoCanvas { canvas ->
                            val brush = Brush.radialGradient(
                                radius = this.size.maxDimension * .9f,
                                center = Offset(
                                    this.size.width / 2f,
                                    this.size.height / 2f
                                ),
                                colors = listOf(
                                    shadowColor.copy(alpha = 0.1f),
                                    shadowColor,
                                    shadowColor,
                                )
                            )
                            this.drawRoundRect(brush = brush)
                        }
                    }
                } else Modifier
            )
            .innerShadow(shape) {
                this.color = shadowColor
                this.radius = innerRadius
                this.spread = 10f
            }
            .graphicsLayer {
                this.shape = shape
                this.scaleX = scale
                this.scaleY = scale
                if (blur > 0f) {
                    renderEffect = BlurEffect(blur, blur, TileMode.Decal)
                }
            }
            .shimmerBorder(
                cornerRadius = buttonDp,
                animatedTranslation = translateAnim
            )
    )
}
