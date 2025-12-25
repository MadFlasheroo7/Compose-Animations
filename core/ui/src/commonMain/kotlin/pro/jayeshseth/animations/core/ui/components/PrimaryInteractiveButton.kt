package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.EaseInCubic
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
//import androidx.compose.animation.graphics.res.animatedVectorResource
//import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
//import androidx.compose.animation.graphics.vector.AnimatedImageVector
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
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
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.ui.modifiers.shimmerBorder
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalHazeApi::class)
@Composable
fun PrimaryInteractiveButton(
    hazeState: HazeState,
    text: String,
    modifier: Modifier = Modifier,
    hazeStyle: HazeStyle = HazeStyle.Unspecified,
    onLongClick: () -> Unit = {},
    clickDelay: Long = 400,
    color: Color = Color.Cyan,
    flip: Boolean = false,
    scale: Float,
    blur: Float,
    onClick: () -> Unit,
) {
    var clickTracker by remember { mutableIntStateOf(0) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val buttonInteracted = isPressed.or(isHovered || clickTracker > 0)
//    val buttonInteracted = true
    var canExecute by remember { mutableStateOf(false) }

    val buttonDp by animateDpAsState(
        targetValue = if (buttonInteracted xor flip) {
            100.dp
        } else {
            20.dp
        },
        animationSpec = tween(500),
        label = "animate button shape",
    )

    val shadowColor by animateColorAsState(
        targetValue = if (buttonInteracted) color else color.copy(.40f),
        animationSpec = tween(500),
        label = "animated button color",
    )
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    var moffsetX by remember { mutableStateOf(0f) }
    var moffsetY by remember { mutableStateOf(0f) }
    val spread by remember { mutableStateOf(Animatable(0f)) }

//    val avd = rememberAnimatedVectorPainter(
//        animatedImageVector = AnimatedImageVector.animatedVectorResource(R.drawable.avd),
//        atEnd = !buttonInteracted
//    )

    val shape by remember(buttonDp, flip) { mutableStateOf(RoundedCornerShape(buttonDp)) }

    LaunchedEffect(Unit) {
//        Log.d("TAG", "PrimaryInteractiveButton:flip: $flip text: $text dp:$buttonDp")
    }

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
                delay(clickDelay)
                spread.snapTo(0f)
                offsetX.snapTo(0f)
                offsetY.snapTo(0f)
                if (canExecute) onClick()
                if (canExecute) canExecute = false
                clickTracker = 0
            }
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5500, easing = EaseInCubic),
            repeatMode = RepeatMode.Restart
        ),
        label = "translate"
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
//            .glowingShadow(
//                borderRadius = buttonDp,
//                color = shadowColor,
//                spread = spread.value.dp,
//                blurRadius = 10.dp,
//                offsetX = offsetX.value.dp,
//                offsetY = offsetY.value.dp
//            )
            .clip(shape)
            .hazeEffect(state = hazeState, style = hazeStyle)
//            .graphicsLayer {
//                this.shape = shape
//                scaleX = scale
//                scaleY = scale
//                renderEffect = BlurEffect(
//                    blur,
//                    blur,
//                    TileMode.Decal
//                )
//            }
            .combinedClickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = {
                    canExecute = true
                    clickTracker++
                },
                onLongClick = {
//                    canExecute = true
                    clickTracker++
                }
            )

//            .glowingShadow(
//                borderRadius = buttonDp,
//                color = shadowColor,
//            )
            .innerShadow(shape) {
                this.color = shadowColor
                this.radius = 10f
                this.spread = 10f
            }
            .graphicsLayer {
                this.shape = shape
                scaleX = scale
                scaleY = scale
                renderEffect = BlurEffect(
                    blur,
                    blur,
                    TileMode.Decal
                )
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
//            Icon(
//                painter = avd,
//                contentDescription = "",
//                tint = color,
//                modifier = Modifier.size(60.dp)
//            )

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
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(120) {
                    PrimaryInteractiveButton(
                        hazeState,
                        color = Color.Magenta,
                        scale = 1f,
                        blur = 0f,
                        text = "Shapes & Morphing", onClick = {
                            println("✨ Animation complete onclick triggred!")
                        })
                }
            }
        }
    }
}