package pro.jayeshseth.animations.easterEggs.navigation

import androidx.navigation3.runtime.EntryProviderScope
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.easterEggs.screens.EasterEggScreen
import pro.jayeshseth.animations.easterEggs.screens.PhysicsLayoutAboutScreen

fun EntryProviderScope<Route>.easterEggs(
    hazeState: HazeState,
    onNavAction: OnNavAction
) {
    entry<EasterEggsRoutes.EasterEggsLandingRoute> {
        EasterEggScreen(hazeState, onNavAction)
    }
    entry<EasterEggsRoutes.PhysicsLayoutAboutRoute> {
        PhysicsLayoutAboutScreen(hazeState)
    }
}