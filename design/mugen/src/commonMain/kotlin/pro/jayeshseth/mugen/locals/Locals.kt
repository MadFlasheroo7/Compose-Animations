package pro.jayeshseth.mugen.locals

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import pro.jayeshseth.mugen.capabilities.MugenCapabilities
import pro.jayeshseth.mugen.look.MugenLook
import pro.jayeshseth.mugen.renderers.MugenRendererSet
import pro.jayeshseth.mugen.renderers.plain.PlainRenderers
import pro.jayeshseth.mugen.tokens.MugenColors
import pro.jayeshseth.mugen.tokens.MugenElevation
import pro.jayeshseth.mugen.tokens.MugenMotion
import pro.jayeshseth.mugen.tokens.MugenShapes
import pro.jayeshseth.mugen.tokens.MugenSpacing
import pro.jayeshseth.mugen.tokens.MugenTypography

private const val NO_THEME_MSG =
    "MugenTheme not provided. Wrap your composition in MugenTheme { … }."

/**
 * The active [MugenLook]. Provided by [pro.jayeshseth.mugen.MugenTheme]. Read this
 * (and downcast) when you need access to Look-specific bespoke tokens from generic code:
 *
 * ```
 * val haze = LocalMugenLook.current as? HazeLook
 * val glow = haze?.colors?.glow
 * ```
 */
val LocalMugenLook = staticCompositionLocalOf<MugenLook> { error(NO_THEME_MSG) }

// ----- Base token locals (mirrors of the active Look's tokens) -----

val LocalMugenColors = staticCompositionLocalOf<MugenColors> { error(NO_THEME_MSG) }
val LocalMugenShapes = staticCompositionLocalOf<MugenShapes> { error(NO_THEME_MSG) }
val LocalMugenMotion = staticCompositionLocalOf<MugenMotion> { error(NO_THEME_MSG) }
val LocalMugenTypography = staticCompositionLocalOf<MugenTypography> { error(NO_THEME_MSG) }
val LocalMugenSpacing = staticCompositionLocalOf<MugenSpacing> { error(NO_THEME_MSG) }
val LocalMugenElevation = staticCompositionLocalOf<MugenElevation> { error(NO_THEME_MSG) }

// ----- Renderer set (the active drawing layer, independent of Look) -----

/**
 * The active [MugenRendererSet]. Provided by [pro.jayeshseth.mugen.MugenTheme].
 * Components read their renderer from here — not from the Look.
 *
 * Defaults to [PlainRenderers] so compositions without an explicit `MugenTheme` still
 * render (useful in previews and tests).
 */
val LocalMugenRendererSet = staticCompositionLocalOf<MugenRendererSet> { PlainRenderers }

// ----- Single Defaults Container for ALL component defaults -----

/**
 * The lazy [MugenDefaultsContainer] provided by [pro.jayeshseth.mugen.MugenTheme].
 * Resolves component layout defaults dynamically on demand.
 */
val LocalMugenDefaults = staticCompositionLocalOf<MugenDefaultsContainer> { error(NO_THEME_MSG) }

// ----- Optional per-component sub-tree override locals -----

val LocalMugenButtonDefaults = compositionLocalOf<MugenButtonDefaults?> { null }
val LocalMugenCardDefaults = compositionLocalOf<MugenCardDefaults?> { null }
val LocalMugenTextDefaults = compositionLocalOf<MugenTextDefaults?> { null }

// ----- Capabilities (target-specific fingerprint) -----

val LocalMugenCapabilities = staticCompositionLocalOf<MugenCapabilities> { error(NO_THEME_MSG) }
