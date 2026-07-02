package pro.jayeshseth.mugen.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pro.jayeshseth.mugen.locals.LocalMugenCardDefaults
import pro.jayeshseth.mugen.locals.LocalMugenRendererSet
import pro.jayeshseth.mugen.renderers.MugenCardRenderer

@Composable
fun MugenCard(
    modifier: Modifier = Modifier,
    renderer: MugenCardRenderer? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val defaults = LocalMugenCardDefaults.current
    val activeRenderer = renderer
        ?: defaults.renderer
        ?: LocalMugenRendererSet.current.card
    activeRenderer.Render(
        defaults = defaults,
        modifier = modifier,
        content = content,
    )
}
