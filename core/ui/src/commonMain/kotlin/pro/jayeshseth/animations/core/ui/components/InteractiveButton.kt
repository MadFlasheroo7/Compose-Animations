package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.trace
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationState

/**
 * A highly interactive and visually appealing button with complex animations.
 *
 * This button features several animations that trigger on interaction (hover or press):
 * - The corner radius animates from a small value to a large one, morphing the shape.
 * - A glowing shadow effect animates its color, spread, and offset.
 * - An inner shadow provides depth.
 * - A continuous shimmer effect animates along the border.
 * - A "haze" effect is applied to the background, blurring content behind it.
 *
 * The click action is delayed until the main interaction animation completes to ensure a smooth
 * visual experience.
 *
 * @param hazeState The [HazeState] to control the background blur effect. The composable's
 *   parent should use `hazeSource` for this to work.
 * @param text The text to display inside the button.
 * @param modifier The [Modifier] to be applied to the button.
 * @param hazeStyle The [HazeStyle] to customize the appearance of the haze effect.
 * @param onLongClick A lambda to be executed on a long click. Note that this also triggers
 *   the main interaction animation.
 * @param color The primary color for the button's text, shadow, and shimmer effect.
 * @param clickDelay A delay in milliseconds after the interaction animation finishes before
 *   the `onClick` lambda is executed.
 * @param height The height of the button.
 * @param onClick A lambda to be executed on a regular click, after the interaction animation
 *   and any specified [clickDelay].
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InteractiveButton(
    hazeState: HazeState,
    text: String,
    modifier: Modifier = Modifier,
    hazeStyle: HazeStyle = cardStyle,
    onLongClick: () -> Unit = {},
    clickDelay: Long = 0,
    height: Dp = 60.dp,
    onClick: () -> Unit
) {
    val customizationState = LocalCustomizationState.current
    val color by animateColorAsState(
        targetValue = Color(customizationState.buttonAccentColorArgb),
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    trace("BaseInteractiveButton") {
        BaseInteractiveButton(
            hazeState = hazeState,
            hazeStyle = hazeStyle,
            onLongClick = onLongClick,
            color = color,
            clickDelay = clickDelay,
            onClick = onClick,
            blur = 0f,
            scale = 1f,
            modifier = modifier
                .height(height)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = text,
                    color = color,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
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
            LazyColumn(
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(120) {
                    InteractiveButton(
                        hazeState,
                        text = "Shapes & Morphing", onClick = {
                            println("✨ Animation complete onclick triggred!")
                        })
                }
            }
        }
    }
}

private val cardStyle = HazeStyle(
    tint = HazeTint(Color.Black.copy(.7f)),
    blurRadius = 80.dp,
    noiseFactor = .5f,
)