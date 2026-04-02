package pro.jayeshseth.animations

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.ComposeUIViewController
import pro.jayeshseth.animations.core.model.CustomizationState
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationRepository
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationState
import pro.jayeshseth.animations.core.ui.theme.LocalEasterEggRepository
import pro.jayeshseth.animations.core.utils.rememberCustomizationRepository
import pro.jayeshseth.animations.core.utils.rememberEasterEggRepository
import pro.jayeshseth.animations.navigation.LandingRoutes
import pro.jayeshseth.animations.navigation.NavGraph
import pro.jayeshseth.animations.navigation.initializeApp

/**
 * The main entry point for the iOS application.
 *
 * This function sets up the root view controller using Compose Multiplatform's
 * `ComposeUIViewController`. It initializes the navigation stack with a `rememberNavigator`,
 * sets the initial screen to `LandingRoutes.Home`, and wraps the entire UI
 * in the `AnimationsTheme`. The `NavGraph` composable is then used to handle
 * navigation logic based on the provided back stack.
 *
 * @return A `UIViewController` that hosts the Compose UI.
 */
fun MainViewController() = ComposeUIViewController {
    initializeApp()
    val backStack = rememberNavigator(LandingRoutes.Home)
    val repo = rememberCustomizationRepository()
    val easterEggRepo = rememberEasterEggRepository()
    val customizationState by repo.state.collectAsState(initial = CustomizationState())
    CompositionLocalProvider(
        LocalCustomizationState provides customizationState,
        LocalCustomizationRepository provides repo,
        LocalEasterEggRepository provides easterEggRepo,
    ) {
        AnimationsTheme {
            NavGraph(backStack) { }
        }
    }
}
