package pro.jayeshseth.mugen.sample

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import pro.jayeshseth.mugen.locals.MugenButtonDefaults
import pro.jayeshseth.mugen.renderers.MugenButtonRenderer
import pro.jayeshseth.mugen.state.MugenButtonState
import androidx.compose.ui.unit.dp

/**
 * Illustrative renderer — draws a thin magenta outline. Used in
 * [MugenSamplePlayground] to demonstrate the per-call renderer slot.
 */
object DebugOutlineButtonRenderer : MugenButtonRenderer {

    @Composable
    override fun Render(
        state: MugenButtonState,
        defaults: MugenButtonDefaults,
        modifier: Modifier,
        onClick: () -> Unit,
        content: @Composable RowScope.() -> Unit,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .defaultMinSize(minHeight = defaults.minHeight, minWidth = defaults.minWidth)
                .clip(defaults.shape)
                .border(BorderStroke(2.dp, Color.Magenta), shape = defaults.shape)
                .clickable(
                    interactionSource = state.interactionSource,
                    indication = null,
                    enabled = state.enabled,
                    onClick = onClick,
                )
                .padding(defaults.contentPadding),
        ) {
            Row(content = content)
        }
    }
}
