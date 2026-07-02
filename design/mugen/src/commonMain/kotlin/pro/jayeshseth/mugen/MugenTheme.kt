package pro.jayeshseth.mugen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import pro.jayeshseth.mugen.capabilities.MugenCapabilities
import pro.jayeshseth.mugen.capabilities.resolveMugenCapabilities
import pro.jayeshseth.mugen.locals.LocalMugenButtonDefaults
import pro.jayeshseth.mugen.locals.LocalMugenCapabilities
import pro.jayeshseth.mugen.locals.LocalMugenCardDefaults
import pro.jayeshseth.mugen.locals.LocalMugenColors
import pro.jayeshseth.mugen.locals.LocalMugenElevation
import pro.jayeshseth.mugen.locals.LocalMugenLook
import pro.jayeshseth.mugen.locals.LocalMugenMotion
import pro.jayeshseth.mugen.locals.LocalMugenRendererSet
import pro.jayeshseth.mugen.locals.LocalMugenShapes
import pro.jayeshseth.mugen.locals.LocalMugenSpacing
import pro.jayeshseth.mugen.locals.LocalMugenTextDefaults
import pro.jayeshseth.mugen.locals.LocalMugenTypography
import pro.jayeshseth.mugen.look.MugenLook
import pro.jayeshseth.mugen.look.plain.PlainLook
import pro.jayeshseth.mugen.renderers.MugenRendererSet
import pro.jayeshseth.mugen.renderers.plain.PlainRenderers
import pro.jayeshseth.mugen.tokens.MugenColors
import pro.jayeshseth.mugen.tokens.MugenElevation
import pro.jayeshseth.mugen.tokens.MugenMotion
import pro.jayeshseth.mugen.tokens.MugenShapes
import pro.jayeshseth.mugen.tokens.MugenSpacing
import pro.jayeshseth.mugen.tokens.MugenTypography

/**
 * Top-level mugen theme. Wires the active [MugenLook] (visual identity) and
 * [MugenRendererSet] (drawing layer) into the composition independently.
 *
 * The two concepts are deliberately decoupled:
 * - **[look]** defines the visual language (colors, shapes, motion, typography, spacing, elevation).
 * - **[renderers]** defines how components are drawn. If a renderer set doesn't cover a
 *   component, [PlainRenderers] provides the fallback via `MugenRendererSet`'s interface defaults.
 *
 * Swapping [look] alone updates all token-driven visuals in one Compose pass; swapping
 * [renderers] alone updates drawing logic without changing the palette.
 *
 * @param look       The visual language to apply. Defaults to [PlainLook] (zero external deps).
 * @param renderers  The drawing layer to apply. Defaults to [PlainRenderers].
 * @param overrides  Theme-wide tweaks to component defaults (sizes, padding, shape, …).
 *                   Sub-tree-only overrides should use `CompositionLocalProvider(Local… provides …)`
 *                   inside [content] directly.
 */
@Composable
fun MugenTheme(
    look: MugenLook = PlainLook(),
    renderers: MugenRendererSet = PlainRenderers,
    overrides: MugenOverrides = MugenOverrides.Empty,
    content: @Composable () -> Unit,
) {
    val buttonDefaults = remember(look, overrides) { look.applyButtonDefaults(overrides) }
    val cardDefaults = remember(look, overrides) { look.applyCardDefaults(overrides) }
    val textDefaults = remember(look, overrides) { look.applyTextDefaults(overrides) }
    val capabilities = resolveMugenCapabilities()

    CompositionLocalProvider(
        LocalMugenLook provides look,
        LocalMugenColors provides look.colors,
        LocalMugenShapes provides look.shapes,
        LocalMugenMotion provides look.motion,
        LocalMugenTypography provides look.typography,
        LocalMugenSpacing provides look.spacing,
        LocalMugenElevation provides look.elevation,
        LocalMugenRendererSet provides renderers,
        LocalMugenButtonDefaults provides buttonDefaults,
        LocalMugenCardDefaults provides cardDefaults,
        LocalMugenTextDefaults provides textDefaults,
        LocalMugenCapabilities provides capabilities,
        content = content,
    )
}

/**
 * Convenience accessor for the active mugen theme's tokens. Lets call sites read tokens
 * with property syntax instead of [LocalMugenColors.current]-style lookups:
 *
 * ```
 * MugenText("Hi", color = MugenTheme.colors.accent)
 * MugenCard { MugenText("Body", style = MugenTheme.typography.bodyLarge) }
 * ```
 *
 * Every getter is `@ReadOnlyComposable` — reading the token does not itself trigger
 * recomposition. Co-exists with the `MugenTheme(...)` composable above (Kotlin keeps
 * functions and objects in separate symbol namespaces; same trick `MaterialTheme` uses).
 */
object MugenTheme {
    val colors: MugenColors
        @Composable @ReadOnlyComposable get() = LocalMugenColors.current

    val shapes: MugenShapes
        @Composable @ReadOnlyComposable get() = LocalMugenShapes.current

    val motion: MugenMotion
        @Composable @ReadOnlyComposable get() = LocalMugenMotion.current

    val typography: MugenTypography
        @Composable @ReadOnlyComposable get() = LocalMugenTypography.current

    val spacing: MugenSpacing
        @Composable @ReadOnlyComposable get() = LocalMugenSpacing.current

    val elevation: MugenElevation
        @Composable @ReadOnlyComposable get() = LocalMugenElevation.current

    /** The currently active Look. Downcast (e.g. `as? HazeLook`) when you need bespoke tokens. */
    val look: MugenLook
        @Composable @ReadOnlyComposable get() = LocalMugenLook.current

    /** The currently active renderer set. */
    val renderers: MugenRendererSet
        @Composable @ReadOnlyComposable get() = LocalMugenRendererSet.current

    val capabilities: MugenCapabilities
        @Composable @ReadOnlyComposable get() = LocalMugenCapabilities.current
}
