package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

/**
 * A segmented button with a glassmorphism ("haze") effect, designed to be used within a
 * `SingleChoiceSegmentedButtonRow`. This component leverages the `haze-jetpack-compose` library
 * to create a frosted glass look that blurs the content behind it.
 *
 * It features smooth animations for selection state changes, including color, tint, noise factor,
 * and an inner shadow, providing a visually engaging user experience.
 *
 * This composable should be used as a child of `SegmentedButtonRow`.
 *
 * @param hazeState The [HazeState] that controls the haze effect. This should be shared
 *   with the content that appears behind the button.
 * @param selected Whether this button is currently selected.
 * @param onClick The callback to be invoked when this button is clicked.
 * @param shape The shape of the button.
 * @param modifier The [Modifier] to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 *   clickable.
 * @param color The primary color used for the content and the inner shadow when selected.
 * @param interactionSource The [MutableInteractionSource] that represents the stream of
 *   [Interaction]s for this button. You can create and pass in your own `remember`ed
 *   instance to observe `Interaction`s and customize the appearance / behavior of this button in
 *   different states.
 * @param icon The icon to be displayed in the button. By default, it uses [SegmentedButtonDefaults.Icon].
 * @param label The label to be displayed in the button.
 */
@Composable
fun SingleChoiceSegmentedButtonRowScope.HazedSegmentedButton(
    hazeState: HazeState,
    selected: Boolean,
    onClick: () -> Unit,
    shape: Shape,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = Color.Cyan,
    interactionSource: MutableInteractionSource? = null,
    icon: @Composable () -> Unit = { SegmentedButtonDefaults.Icon(selected) },
    label: @Composable () -> Unit,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
//    val interactionCount = interactionSource.interactionCountAsState()
    val shadowColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(.40f),
        animationSpec = tween(500),
        label = "animated shadow color",
    )
    val tint by animateColorAsState(
        targetValue = if (selected) Color.Black.copy(.7f) else Color.Black.copy(.5f),
        animationSpec = tween(500),
        label = "animated shadow color",
    )
    val noise by animateFloatAsState(
        targetValue = if (selected) .5f else -0f,
        animationSpec = tween(500),
        label = "animated noise"
    )

    Surface(
        modifier =
            modifier
                .weight(1f)
//                .interactionZIndex(selected, interactionCount)
                .zIndex(
                    if (selected) 1f else 0f
                )
                .defaultMinSize(
                    minWidth = ButtonDefaults.MinWidth,
                    minHeight = ButtonDefaults.MinHeight
                )
                .semantics { role = Role.RadioButton },
        selected = selected,
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        color = Color.Transparent,
        contentColor = color,
        interactionSource = interactionSource
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .hazeEffect(
                    state = hazeState,
                    style = HazeStyle(
                        tint = HazeTint(tint),
                        noiseFactor = noise
                    )
                )
                .innerShadow(
                    shape
                ) {
                    this.spread = 10f
                    this.radius = 10f
                    this.color = shadowColor
                }
                .padding(12.dp)
                .wrapContentSize(unbounded = true)
                .animateContentSize()
        ) {
            icon()
            label()
        }
    }
}
