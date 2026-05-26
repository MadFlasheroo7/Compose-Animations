package pro.jayeshseth.mugen.state

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember

/**
 * Lightweight, renderer-agnostic state for a [pro.jayeshseth.mugen.components.MugenButton].
 *
 * Created once inside the component and passed into the renderer's `Render` function so
 * the renderer never has to read interaction sources itself.
 */
@Stable
class MugenButtonState internal constructor(
    val interactionSource: MutableInteractionSource,
    private val pressedState: State<Boolean>,
    private val hoveredState: State<Boolean>,
    private val focusedState: State<Boolean>,
    val enabled: Boolean,
) {
    val isPressed: Boolean get() = pressedState.value
    val isHovered: Boolean get() = hoveredState.value
    val isFocused: Boolean get() = focusedState.value

    /** True when any user interaction is currently active. */
    val isInteracting: Boolean get() = isPressed || isHovered || isFocused
}

/**
 * Remembers a [MugenButtonState] derived from the given [interactionSource].
 */
@Composable
fun rememberMugenButtonState(
    interactionSource: MutableInteractionSource,
    enabled: Boolean,
): MugenButtonState {
    val pressed = interactionSource.collectIsPressedAsState()
    val hovered = interactionSource.collectIsHoveredAsState()
    val focused = interactionSource.collectIsFocusedAsState()
    return remember(interactionSource, enabled, pressed, hovered, focused) {
        MugenButtonState(interactionSource, pressed, hovered, focused, enabled)
    }
}
