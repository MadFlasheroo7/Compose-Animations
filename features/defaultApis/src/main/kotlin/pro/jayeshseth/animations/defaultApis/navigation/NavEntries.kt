package pro.jayeshseth.animations.defaultApis.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.defaultApis.animations.infiniteTransistions.InfiniteRotation
import pro.jayeshseth.animations.defaultApis.screens.AnimateValueAsState
import pro.jayeshseth.animations.defaultApis.screens.AnimatedGestures
import pro.jayeshseth.animations.defaultApis.screens.AnimatedTransition
import pro.jayeshseth.animations.defaultApis.screens.VisibilityAnimations

fun EntryProviderBuilder<Route>.defaultApis(
    onClickLink: OnClickLink
) {
    entry<DefaultApisRoutes.AnimateVisibilityRoute> {
        VisibilityAnimations(onClickLink)
    }
    entry<DefaultApisRoutes.AnimateContentRoute> {
        AnimatedTransition()
    }
    entry<DefaultApisRoutes.AnimateValueAsStateRoute> { AnimateValueAsState(onClickLink) }
    entry<DefaultApisRoutes.AnimateGestureRoute> { AnimatedGestures() }
    entry<DefaultApisRoutes.InfiniteRotationRoute> { InfiniteRotation() }
}