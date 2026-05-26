package pro.jayeshseth.mugen.renderers.haze

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import pro.jayeshseth.glowingButton.glowingShadow
import pro.jayeshseth.mugen.locals.MugenButtonDefaults
import pro.jayeshseth.mugen.look.haze.HazeLook
import pro.jayeshseth.mugen.renderers.MugenButtonRenderer
import pro.jayeshseth.mugen.state.MugenButtonState

/**
 * Renders [pro.jayeshseth.mugen.components.MugenButton] in the Haze visual language:
 * tinted "glass" fill, animated corner morph on press, outer glow halo, and an inner
 * shadow that punches in when interacting.
 *
 * The renderer reads bespoke tokens directly off its typed [HazeLook] reference —
 * `colors.glow`, `colors.hazeTint`, `colors.innerShadow`, `motion.cornerMorph`,
 * `motion.glowPulse`, `elevation.glowSpread`, `elevation.innerShadowRadius`.
 *
 * Note (indev01): a full glassmorphic blur via the `haze` library requires the consumer
 * to wire a `HazeState` + `hazeSource` on a background layer; this renderer ships
 * without that hookup in Phase A and uses a tinted fill as a fallback. Phase B will add
 * a `LocalMugenHazeContext` so the renderer can pick up an ambient state and produce
 * the actual blur.
 */
class HazeButtonRenderer(private val look: HazeLook) : MugenButtonRenderer {

    @Composable
    override fun Render(
        state: MugenButtonState,
        defaults: MugenButtonDefaults,
        modifier: Modifier,
        onClick: () -> Unit,
        content: @Composable RowScope.() -> Unit,
    ) {
        val interacting = state.isInteracting

        val cornerDp by animateDpAsState(
            targetValue = if (interacting) look.shapes.interactiveMorphActive
            else look.shapes.interactiveMorphRest,
            animationSpec = look.motion.cornerMorph,
        )
        val shape = RoundedCornerShape(cornerDp)

        val glowAlpha by animateFloatAsState(
            targetValue = if (interacting) 1f else 0.4f,
            animationSpec = look.motion.glowPulse,
        )
        val animatedGlow = look.colors.glow.copy(alpha = glowAlpha)

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .defaultMinSize(
                    minHeight = defaults.minHeight,
                    minWidth = defaults.minWidth,
                )
                .glowingShadow {
                    color = animatedGlow
                    spread = look.elevation.glowSpread
                    blurRadius = look.elevation.glowRadius
                }
                .clip(shape)
                .background(look.colors.hazeTint)
                .innerShadow(shape) {
                    color = look.colors.innerShadow.copy(alpha = if (interacting) 0.7f else 0.3f)
                    spread = look.elevation.innerShadowSpread
                    radius = look.elevation.innerShadowRadius
                }
                .clickable(
                    interactionSource = state.interactionSource,
                    indication = null,
                    enabled = state.enabled,
                    onClick = onClick,
                )
                .padding(defaults.contentPadding),
        ) {
            CompositionLocalProvider(LocalContentColor provides look.colors.onSurface) {
                Row(content = content)
            }
        }
    }
}
