package pro.jayeshseth.mugen.renderers.haze

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import pro.jayeshseth.mugen.locals.MugenCardDefaults
import pro.jayeshseth.mugen.look.haze.HazeLook
import pro.jayeshseth.mugen.renderers.MugenCardRenderer

class HazeCardRenderer(private val look: HazeLook) : MugenCardRenderer {
    @Composable
    override fun Render(
        defaults: MugenCardDefaults,
        modifier: Modifier,
        content: @Composable ColumnScope.() -> Unit,
    ) {
        Column(
            modifier = modifier
                .clip(defaults.shape)
                .background(look.colors.hazeTint)
                .innerShadow(defaults.shape) {
                    color = look.colors.innerShadow.copy(alpha = 0.25f)
                    spread = look.elevation.innerShadowSpread * 0.4f
                    radius = look.elevation.innerShadowRadius * 1.2f
                }
                .padding(defaults.contentPadding),
        ) {
            CompositionLocalProvider(LocalContentColor provides look.colors.onSurface) {
                content()
            }
        }
    }
}
