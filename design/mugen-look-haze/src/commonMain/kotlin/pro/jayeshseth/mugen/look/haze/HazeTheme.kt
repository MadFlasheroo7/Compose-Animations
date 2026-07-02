package pro.jayeshseth.mugen.look.haze

import androidx.compose.runtime.Composable
import pro.jayeshseth.mugen.MugenOverrides
import pro.jayeshseth.mugen.MugenTheme
import pro.jayeshseth.mugen.renderers.haze.HazeRendererSet

/**
 * Convenience wrapper that pairs [HazeLook] + [HazeRendererSet] and delegates to
 * [MugenTheme]. Use this for zero-friction Haze theming — no need to manually supply
 * both `look` and `renderers`:
 *
 * ```
 * HazeTheme {
 *     MugenButton(onClick = {}) { MugenText("Click me") }
 * }
 * ```
 *
 * For fine-grained control (custom tokens, custom palette, mixed renderer sets):
 * ```
 * MugenTheme(look = HazeLook(colors = HazeColors(primary = Color.Red)),
 *            renderers = HazeRendererSet()) { … }
 * ```
 *
 * @param look       Haze token palette. Defaults to stock [HazeLook].
 * @param renderers  Haze renderer set. Defaults to [HazeRendererSet] built from [look].
 * @param overrides  Theme-wide component-defaults tweaks.
 */
@Composable
fun HazeTheme(
    look: HazeLook = HazeLook(),
    renderers: HazeRendererSet = HazeRendererSet(look),
    overrides: MugenOverrides = MugenOverrides.Empty,
    content: @Composable () -> Unit,
) {
    MugenTheme(look = look, renderers = renderers, overrides = overrides, content = content)
}
