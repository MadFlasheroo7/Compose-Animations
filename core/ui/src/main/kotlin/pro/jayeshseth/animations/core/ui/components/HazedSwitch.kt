package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composeunstyled.Thumb
import com.composeunstyled.ToggleSwitch
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun HazedSwitch(
    hazeState: HazeState,
    toggled: Boolean = true,
    onToggleChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = false, //TODO add enable and disable states
    color: Color = Color.Cyan
) {
    val alpha by animateFloatAsState(
        targetValue = if (toggled) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    )
    val shadowColor by animateColorAsState(
        targetValue = if (toggled) color else color.copy(.4f),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    )
    val spread by animateFloatAsState(
        targetValue = if (toggled) 10f else 5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    )
    val radius by animateFloatAsState(
        targetValue = if (toggled) 10f else 5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    )
    ToggleSwitch(
        toggled = toggled,
        onToggled = onToggleChanged,
        enabled = enabled,
        modifier = modifier
            .width(58.dp)
            .padding(vertical = 4.dp)
            .dropShadow(RoundedCornerShape(100.dp)) {
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        color.copy(alpha = .2f),
                        color.copy(alpha = .4f),
                        color.copy(alpha = .6f),
                        color.copy(alpha = .8f),
                        color.copy(alpha = 1f),
                    )
                )
                this.radius = 40f
                this.spread = 15f
                this.alpha = alpha
            }
            .clip(RoundedCornerShape(50.dp))
            .hazeEffect(
                state = hazeState,
                HazeStyle(
                    tint = HazeTint(Color.Black.copy(.7f)),
                    blurRadius = 50.dp,
                    noiseFactor = .2f,
                )
            ),
        contentPadding = PaddingValues(4.dp),
    ) {
        Thumb(
            shape = CircleShape,
            color = Color.Transparent,
            modifier = Modifier
                .clip(CircleShape)
                .hazeEffect(
                    state = hazeState,
                )
                .innerShadow(CircleShape) {
                    this.spread = spread
                    this.radius = radius
                    this.color = shadowColor
                },
        )
    }

}

@Preview
@Composable
private fun Preview() {
    var toggled by remember { mutableStateOf(true) }
    ShaderPreviewContent() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(500.dp)
                .hazeEffect(
                    state = it,
                    HazeStyle(
                        tint = HazeTint(Color.Black.copy(.4f)),
                        blurRadius = 50.dp,
                        noiseFactor = .1f,
                    )
                )
        ) {
            HazedSwitch(it, toggled, { toggled = it })
        }
    }
}