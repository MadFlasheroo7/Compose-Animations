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
import pro.jayeshseth.mugen.locals.LocalMugenShapes
import pro.jayeshseth.mugen.locals.LocalMugenSpacing
import pro.jayeshseth.mugen.locals.LocalMugenTextDefaults
import pro.jayeshseth.mugen.locals.LocalMugenTypography
import pro.jayeshseth.mugen.look.MugenLook
import pro.jayeshseth.mugen.look.haze.HazeLook
import pro.jayeshseth.mugen.tokens.MugenColors
import pro.jayeshseth.mugen.tokens.MugenElevation
import pro.jayeshseth.mugen.tokens.MugenMotion
import pro.jayeshseth.mugen.tokens.MugenShapes
import pro.jayeshseth.mugen.tokens.MugenSpacing
import pro.jayeshseth.mugen.tokens.MugenTypography

/**
 * Top-level mugen theme. Wraps any subtree with the active [MugenLook] and every derived
 * per-component default. Swapping [look] is a single state change that re-themes the
 * entire subtree in one Compose pass.
 *
 * @param look       The visual language to apply. Defaults to the signature [HazeLook].
 * @param overrides  Theme-wide tweaks to component defaults (sizes, padding, shape, …).
 *                   Sub-tree-only overrides should use `CompositionLocalProvider(Local… provides …)`
 *                   inside [content] directly.
 */
@Composable
fun MugenTheme(
    look: MugenLook = HazeLook(),
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

    val capabilities: MugenCapabilities
        @Composable @ReadOnlyComposable get() = LocalMugenCapabilities.current
}
