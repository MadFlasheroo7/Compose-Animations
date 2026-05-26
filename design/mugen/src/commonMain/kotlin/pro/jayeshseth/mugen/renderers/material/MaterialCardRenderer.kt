package pro.jayeshseth.mugen.renderers.material

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
import pro.jayeshseth.mugen.locals.MugenCardDefaults
import pro.jayeshseth.mugen.look.material.MaterialLook
import pro.jayeshseth.mugen.renderers.MugenCardRenderer

class MaterialCardRenderer(private val look: MaterialLook) : MugenCardRenderer {
    @Composable
    override fun Render(
        defaults: MugenCardDefaults,
        modifier: Modifier,
        content: @Composable ColumnScope.() -> Unit,
    ) {
        Column(
            modifier = modifier
                .shadow(elevation = look.elevation.raised, shape = defaults.shape)
                .clip(defaults.shape)
                .background(look.colors.surfaceVariant)
                .padding(defaults.contentPadding),
        ) {
            CompositionLocalProvider(LocalContentColor provides look.colors.onSurfaceVariant) {
                content()
            }
        }
    }
}
