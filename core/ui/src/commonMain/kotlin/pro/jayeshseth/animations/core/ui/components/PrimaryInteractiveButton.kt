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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.ui.icons.AnimIcons
import pro.jayeshseth.animations.core.ui.modifiers.glowingShadow
import pro.jayeshseth.animations.core.ui.modifiers.shimmerBorder
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme

// TODO make is accessible and testable
/**
 * A highly interactive and visually complex button Composable.
 *
 * This button features multiple animations that trigger on user interaction (press, hover, or click).
 * Animations include changing corner radius, shadow color, glowing effects, and a shimmering border.
 * It also integrates with the `Haze` library for a frosted glass effect.
 *
 * The `onClick` lambda is executed after a series of animations complete, with a configurable delay.
 *
 * @param hazeState The [HazeState] to apply the glassmorphism (haze) effect.
 * @param text The text to display inside the button.
 * @param modifier The [Modifier] to be applied to the button.
 * @param hazeStyle The [HazeStyle] to customize the appearance of the haze effect.
 * @param onLongClick A lambda to be invoked on a long-click gesture. Note: this currently triggers the same animation as a regular click.
 * @param clickDelay The delay in milliseconds after the interaction animation finishes before the `onClick` lambda is executed.
 * @param color The primary color used for the button's text, border, and shadow effects.
 * @param flip A boolean that inverts the animation target for the button's shape. If true, the button will become more rounded when not interacted with, and less rounded on interaction.
 * @param scale The scale factor to be applied to the button via `graphicsLayer`.
 * @param blur The blur radius to be applied to the button via `graphicsLayer`.
 * @param onClick A lambda to be invoked when the button is clicked and the subsequent animation completes.
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalHazeApi::class)
@Composable
fun PrimaryInteractiveButton(
    hazeState: HazeState,
    text: String,
    modifier: Modifier = Modifier,
    hazeStyle: HazeStyle = HazeStyle.Unspecified,
    onLongClick: () -> Unit = {},
    clickDelay: Long = 0L,
    color: Color = Color.Cyan,
    flip: Boolean = false,
    scale: Float = 1f,
    blur: Float = 0f,
    onClick: () -> Unit,
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
    ) { if (it xor flip) color else color.copy(alpha = .4f) }

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
                                    async { spread.animateTo(20f, tween(500)) },
                                    async { spread2.animateTo(400f, tween(500)) }
                                )

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
            .drawBehind {
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
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 25.dp
            )
        ) {
            Icon(
                painter = painterResource(AnimIcons.settings),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(60.dp)
            )

            Text(
                text = text,
                color = color,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(120) {
                    PrimaryInteractiveButton(
                        hazeState = hazeState,
                        color = Color.Magenta,
                        scale = 1f,
                        blur = 0f,
                        text = "Shapes & Morphing",
                        onClick = {
                            println("✨ Animation complete onclick triggred!")
                        }
                    )
                }
            }
        }
    }
}