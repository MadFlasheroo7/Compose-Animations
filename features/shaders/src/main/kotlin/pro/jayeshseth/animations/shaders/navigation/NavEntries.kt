package pro.jayeshseth.animations.shaders.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.shaders.screens.Shaders
import pro.jayeshseth.animations.shaders.screens.interstellarSpace.InterstellarShaderScreen
import pro.jayeshseth.animations.shaders.screens.rainbowCircle.RainbowCircle

fun EntryProviderBuilder<Route>.shaders(
    onClickLink: OnClickLink,
    onNavAction: OnNavAction
) {
    entry<ShaderRoutes.ShaderGraphRoute> {
        Shaders(navAction = onNavAction)
    }
    entry<ShaderRoutes.RainbowShaderRoute> {
        RainbowCircle(onClickLink)
    }
    entry<ShaderRoutes.InterstellarShaderRoute> {
        InterstellarShaderScreen(onClickLink)
    }
}