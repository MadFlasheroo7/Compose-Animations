package pro.jayeshseth.mugen.look.material

import androidx.compose.runtime.Composable
import pro.jayeshseth.mugen.MugenOverrides
import pro.jayeshseth.mugen.MugenTheme
import pro.jayeshseth.mugen.renderers.material.MaterialRendererSet

/**
 * Convenience wrapper that pairs [MaterialLook] + [MaterialRendererSet] and delegates to
 * [MugenTheme]. Named `MugenMaterialTheme` to avoid clashing with Jetpack Compose's own
 * `MaterialTheme`.
 *
 * ```
 * MugenMaterialTheme {
 *     MugenButton(onClick = {}) { MugenText("Click me") }
 * }
 * ```
 *
 * @param look       Material token palette. Defaults to stock [MaterialLook].
 * @param renderers  Material renderer set. Defaults to [MaterialRendererSet] built from [look].
 * @param overrides  Theme-wide component-defaults tweaks.
 */
@Composable
fun MugenMaterialTheme(
    look: MaterialLook = MaterialLook(),
    renderers: MaterialRendererSet = MaterialRendererSet(look),
    overrides: MugenOverrides = MugenOverrides.Empty,
    content: @Composable () -> Unit,
) {
    MugenTheme(look = look, renderers = renderers, overrides = overrides, content = content)
}
