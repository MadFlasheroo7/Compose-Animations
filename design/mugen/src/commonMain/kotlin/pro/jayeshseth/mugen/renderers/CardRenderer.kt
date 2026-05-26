package pro.jayeshseth.mugen.renderers

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import pro.jayeshseth.mugen.locals.MugenCardDefaults

@Stable
interface MugenCardRenderer {
    @Composable
    fun Render(
        defaults: MugenCardDefaults,
        modifier: Modifier,
        content: @Composable ColumnScope.() -> Unit,
    )
}
