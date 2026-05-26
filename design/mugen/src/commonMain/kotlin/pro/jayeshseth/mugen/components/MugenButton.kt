package pro.jayeshseth.mugen.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import pro.jayeshseth.mugen.locals.LocalMugenButtonDefaults
import pro.jayeshseth.mugen.locals.LocalMugenLook
import pro.jayeshseth.mugen.renderers.MugenButtonRenderer
import pro.jayeshseth.mugen.state.rememberMugenButtonState

/**
 * A button rendered by the active [pro.jayeshseth.mugen.look.MugenLook]. The visual is
 * owned by that Look's renderer; the component itself only owns interaction plumbing.
 *
 * Token values (min height, padding, shape) come exclusively from
 * [LocalMugenButtonDefaults] — to change them, provide your own defaults via
 * `CompositionLocalProvider` or via [pro.jayeshseth.mugen.MugenOverrides]. The only
 * per-call escape hatch is [renderer].
 *
 * @param renderer  Optional per-call renderer override. When set, beats the sub-tree
 *                  default and the Look's renderer.
 */
@Composable
fun MugenButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    renderer: MugenButtonRenderer? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val defaults = LocalMugenButtonDefaults.current
    val activeRenderer = renderer
        ?: defaults.renderer
        ?: LocalMugenLook.current.buttonRenderer
    val state = rememberMugenButtonState(interactionSource = interactionSource, enabled = enabled)
    activeRenderer.Render(
        state = state,
        defaults = defaults,
        modifier = modifier,
        onClick = onClick,
        content = content,
    )
}
