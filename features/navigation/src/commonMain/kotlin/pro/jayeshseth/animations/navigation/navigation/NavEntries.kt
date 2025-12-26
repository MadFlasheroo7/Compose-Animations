package pro.jayeshseth.animations.navigation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.navigation.screens.nav2.AnimatedNavGraph

fun EntryProviderScope<Route>.navigation(hazeState: HazeState) {
    entry<NavigationRoutes.Nav2GraphRoute> {
        AnimatedNavGraph(hazeState)
    }

}