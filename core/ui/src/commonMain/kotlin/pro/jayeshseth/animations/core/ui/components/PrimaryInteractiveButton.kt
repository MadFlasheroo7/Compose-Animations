package pro.jayeshseth.animations.core.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.ui.icons.AnimIcons
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme

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
    BaseInteractiveButton(
        hazeState = hazeState,
        hazeStyle = hazeStyle,
        onLongClick = onLongClick,
        color = color,
        clickDelay = clickDelay,
        onClick = onClick,
        flip = flip,
        blur = blur,
        scale = scale,
        modifier = modifier,
        showOverlay = true
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