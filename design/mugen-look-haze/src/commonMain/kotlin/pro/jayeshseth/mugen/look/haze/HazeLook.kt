package pro.jayeshseth.mugen.look.haze

import androidx.compose.runtime.Stable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.components.MugenChipDefaults
import pro.jayeshseth.mugen.locals.MugenButtonDefaults
import pro.jayeshseth.mugen.locals.MugenCardDefaults
import pro.jayeshseth.mugen.locals.MugenDefaultsRegistry
import pro.jayeshseth.mugen.locals.MugenTextDefaults
import pro.jayeshseth.mugen.locals.mugenDefaults
import pro.jayeshseth.mugen.look.MugenLook

/**
 * The "signature" Haze look — glassmorphic surfaces, glowing shadows, animated corner morph,
 * shimmer borders, inner shadows on press.
 *
 * `HazeLook` is a pure **visual identity** — it carries tokens only. The drawing logic lives
 * in `HazeRendererSet` (same module). Pair them via [pro.jayeshseth.mugen.MugenTheme] or the
 * convenience [HazeTheme] wrapper:
 *
 * ```
 * // Simple — HazeTheme wires look + renderers for you
 * HazeTheme { … }
 *
 * // Advanced — explicit pairing
 * MugenTheme(look = HazeLook(), renderers = HazeRendererSet()) { … }
 * ```
 *
 * Covariant overrides on the token properties expose [HazeColors] / [HazeMotion] / etc.
 * to anyone holding a [HazeLook] reference — so renderers can read bespoke tokens like
 * `colors.glow`, `motion.shimmerSweep`, `elevation.glowSpread`.
 *
 * @param colors  Haze color palette + bespoke `glow` / `shimmer` / `hazeTint` / `innerShadow`.
 * @param shapes  Haze shapes + bespoke `interactiveMorphRest` / `interactiveMorphActive`.
 * @param motion  Haze motion + bespoke `shimmerSweep` / `cornerMorph` / `glowPulse`.
 */
@Stable
class HazeLook(
    override val colors: HazeColors = HazeColors(),
    override val shapes: HazeShapes = HazeShapes(),
    override val motion: HazeMotion = HazeMotion(),
    override val typography: HazeTypography = HazeTypography(),
    override val spacing: HazeSpacing = HazeSpacing(),
    override val elevation: HazeElevation = HazeElevation(),
) : MugenLook {
    override val name: String = "Haze"

    override val defaults: MugenDefaultsRegistry = mugenDefaults {
        register(
            MugenButtonDefaults(
                minHeight = 44.dp,
                minWidth = 56.dp,
                contentPadding = PaddingValues(horizontal = spacing.lg, vertical = spacing.md),
                shape = shapes.lg,
            )
        )
        register(
            MugenCardDefaults(
                contentPadding = PaddingValues(spacing.lg),
                shape = shapes.lg,
            )
        )
        register(MugenTextDefaults())
        register(
            MugenChipDefaults(
                contentPadding = PaddingValues(horizontal = spacing.md, vertical = spacing.sm),
                shape = shapes.sm,
            )
        )
    }
}
