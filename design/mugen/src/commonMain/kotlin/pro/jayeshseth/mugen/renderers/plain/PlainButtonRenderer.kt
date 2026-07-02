package pro.jayeshseth.mugen.renderers.plain

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import pro.jayeshseth.mugen.locals.LocalMugenColors
import pro.jayeshseth.mugen.locals.LocalMugenMotion
import pro.jayeshseth.mugen.locals.MugenButtonDefaults
import pro.jayeshseth.mugen.renderers.MugenButtonRenderer
import pro.jayeshseth.mugen.state.MugenButtonState

/**
 * Plain button renderer — solid primary fill, ripple indication, subtle scale-down on
 * press. Reads all tokens from composition locals (LocalMugenColors, LocalMugenMotion)
 * so it works correctly paired with *any* [pro.jayeshseth.mugen.look.MugenLook].
 */
object PlainButtonRenderer : MugenButtonRenderer {

    @Composable
    override fun Render(
        state: MugenButtonState,
        defaults: MugenButtonDefaults,
        modifier: Modifier,
        onClick: () -> Unit,
        content: @Composable RowScope.() -> Unit,
    ) {
        val colors = LocalMugenColors.current
        val motion = LocalMugenMotion.current

        val pressScale by animateFloatAsState(
            targetValue = if (state.isPressed) 0.97f else 1f,
            animationSpec = motion.snappy,
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .defaultMinSize(minHeight = defaults.minHeight, minWidth = defaults.minWidth)
                .graphicsLayer {
                    scaleX = pressScale
                    scaleY = pressScale
                }
                .clip(defaults.shape)
                .background(if (state.enabled) colors.primary else colors.surfaceVariant)
                .clickable(
                    interactionSource = state.interactionSource,
                    indication = ripple(bounded = true),
                    enabled = state.enabled,
                    onClick = onClick,
                )
                .padding(defaults.contentPadding),
        ) {
            val contentColor = if (state.enabled) colors.onPrimary else colors.onSurfaceVariant
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                Row(content = content)
            }
        }
    }
}
