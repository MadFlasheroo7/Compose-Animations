package pro.jayeshseth.animations.defaultApis.navigation

import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.defaultApis.animations.infiniteTransistions.InfiniteRotation
import pro.jayeshseth.animations.defaultApis.screens.AnimateValueAsState
import pro.jayeshseth.animations.defaultApis.screens.AnimatedGestures
import pro.jayeshseth.animations.defaultApis.screens.AnimatedTransition
import pro.jayeshseth.animations.defaultApis.screens.DefaultApisLanding
import pro.jayeshseth.animations.defaultApis.screens.VisibilityAnimations

fun EntryProviderBuilder<Route>.defaultApis(
    onClickLink: OnClickLink,
    hazeState: HazeState,
    color: Color,
    navAction: OnNavAction,
) {
    entry<DefaultApisRoutes.DefaultApisLanding> {
        DefaultApisLanding(hazeState,
//            color = color
        ) { navAction(it) }
    }
    entry<DefaultApisRoutes.AnimateVisibilityRoute> {
        VisibilityAnimations(hazeState, onClickLink)
    }
    entry<DefaultApisRoutes.AnimateContentRoute> {
        AnimatedTransition(hazeState)
    }
    entry<DefaultApisRoutes.AnimateValueAsStateRoute> {
        AnimateValueAsState(
            hazeState,
            onClickLink
        )
    }
    entry<DefaultApisRoutes.AnimateGestureRoute> { AnimatedGestures() }
    entry<DefaultApisRoutes.InfiniteRotationRoute> { InfiniteRotation(hazeState) }
}