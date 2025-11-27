package pro.jayeshseth.animations.playground.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.playground.screens.AnimationSpecs
import pro.jayeshseth.animations.playground.screens.PlaygroundLanding
import pro.jayeshseth.animations.playground.screens.tweenAndSpring.TweenAndSpringScreen

fun EntryProviderBuilder<Route>.playground(
    hazeState: HazeState,
    onNavAction: OnNavAction,
    onClickLink: OnClickLink
) {
    entry<PlaygroundRoutes.PlaygroundLandingRoute> {
        PlaygroundLanding(hazeState = hazeState, navAction = onNavAction)
    }
    entry<PlaygroundRoutes.AnimationSpecRoute> {
        AnimationSpecs(hazeState = hazeState, navAction = onNavAction)
    }
    entry<PlaygroundRoutes.TweenAndSpringRoute> {
        TweenAndSpringScreen(hazeState = hazeState, onClickLink)
    }
}