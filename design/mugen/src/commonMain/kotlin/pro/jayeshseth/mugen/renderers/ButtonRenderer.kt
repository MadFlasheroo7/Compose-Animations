package pro.jayeshseth.mugen.renderers

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import pro.jayeshseth.mugen.locals.MugenButtonDefaults
import pro.jayeshseth.mugen.state.MugenButtonState

/**
 * Owns the *visual* of a [pro.jayeshseth.mugen.components.MugenButton]. Renderers know
 * which Look they live under (passed as a typed reference at construction time) and can
 * read any bespoke tokens that Look adds.
 *
 * Component composables hand the renderer everything it needs to draw; the renderer never
 * reads CompositionLocals itself.
 */
@Stable
interface MugenButtonRenderer {
    @Composable
    fun Render(
        state: MugenButtonState,
        defaults: MugenButtonDefaults,
        modifier: Modifier,
        onClick: () -> Unit,
        content: @Composable RowScope.() -> Unit,
    )
}
