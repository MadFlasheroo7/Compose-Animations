package pro.jayeshseth.mugen.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import pro.jayeshseth.mugen.locals.LocalMugenButtonDefaults
import pro.jayeshseth.mugen.locals.LocalMugenRendererSet
import pro.jayeshseth.mugen.renderers.MugenButtonRenderer
import pro.jayeshseth.mugen.state.rememberMugenButtonState

/**
 * A button whose visual is owned by the active [pro.jayeshseth.mugen.renderers.MugenRendererSet];
 * the component itself only manages interaction plumbing (press, hover, focus).
 *
 * Renderer resolution priority (highest → lowest):
 * 1. Per-call [renderer] param
 * 2. Sub-tree [pro.jayeshseth.mugen.locals.LocalMugenButtonDefaults].renderer
 * 3. [pro.jayeshseth.mugen.locals.LocalMugenRendererSet].button
 *
 * Token values (min height, padding, shape) come from [LocalMugenButtonDefaults].
 *
 * @param renderer  Optional per-call renderer override (highest priority).
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
        ?: LocalMugenRendererSet.current.button
    val state = rememberMugenButtonState(interactionSource = interactionSource, enabled = enabled)
    activeRenderer.Render(
        state = state,
        defaults = defaults,
        modifier = modifier,
        onClick = onClick,
        content = content,
    )
}
