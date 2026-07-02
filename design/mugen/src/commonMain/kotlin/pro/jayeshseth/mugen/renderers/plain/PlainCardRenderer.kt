package pro.jayeshseth.mugen.renderers.plain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import pro.jayeshseth.mugen.locals.LocalMugenColors
import pro.jayeshseth.mugen.locals.LocalMugenElevation
import pro.jayeshseth.mugen.locals.MugenCardDefaults
import pro.jayeshseth.mugen.renderers.MugenCardRenderer

/**
 * Plain card renderer — surfaceVariant fill, subtle raised shadow. Reads all tokens from
 * composition locals so it works correctly paired with *any* [pro.jayeshseth.mugen.look.MugenLook].
 */
object PlainCardRenderer : MugenCardRenderer {

    @Composable
    override fun Render(
        defaults: MugenCardDefaults,
        modifier: Modifier,
        content: @Composable ColumnScope.() -> Unit,
    ) {
        val colors = LocalMugenColors.current
        val elevation = LocalMugenElevation.current

        Column(
            modifier = modifier
                .shadow(elevation = elevation.raised, shape = defaults.shape)
                .clip(defaults.shape)
                .background(colors.surfaceVariant)
                .padding(defaults.contentPadding),
        ) {
            CompositionLocalProvider(LocalContentColor provides colors.onSurfaceVariant) {
                content()
            }
        }
    }
}
