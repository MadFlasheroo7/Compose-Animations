package pro.jayeshseth.animations.shaders.navigation

import androidx.navigation3.runtime.EntryProviderScope
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.shaders.screens.Shaders
import pro.jayeshseth.animations.shaders.screens.interstellarSpace.InterstellarShaderScreen
import pro.jayeshseth.animations.shaders.screens.rainbowCircle.RainbowCircle

fun EntryProviderScope<Route>.shaders(
    hazeState: HazeState,
    onClickLink: OnClickLink,
    onNavAction: OnNavAction
) {
    entry<ShaderRoutes.ShaderGraphRoute> {
        Shaders(
            hazeState = hazeState,
            navAction = onNavAction
        )
    }
    entry<ShaderRoutes.RainbowShaderRoute> {
        RainbowCircle(
            hazeState,
            onClickLink
        )
    }
    entry<ShaderRoutes.InterstellarShaderRoute> {
        InterstellarShaderScreen(
            hazeState,
            onClickLink
        )
    }
}