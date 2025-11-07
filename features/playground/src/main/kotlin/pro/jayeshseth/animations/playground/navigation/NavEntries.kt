package pro.jayeshseth.animations.playground.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.playground.screens.AnimationSpecs
import pro.jayeshseth.animations.playground.screens.Playground
import pro.jayeshseth.animations.playground.screens.tweenAndSpring.TweenAndSpringScreen

fun EntryProviderBuilder<Route>.playground(
    onNavAction: OnNavAction,
    onClickLink: OnClickLink
) {
    entry<PlaygroundRoutes.PlaygroundRoute> {
        Playground(navAction = onNavAction)
    }
    entry<PlaygroundRoutes.AnimationSpecRoute> {
        AnimationSpecs(navAction = onNavAction)
    }
    entry<PlaygroundRoutes.TweenAndSpringRoute> {
        TweenAndSpringScreen(onClickLink)
    }
}