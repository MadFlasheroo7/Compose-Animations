package pro.jayeshseth.animations

import androidx.compose.ui.window.ComposeUIViewController
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.navigation.LandingRoutes
import pro.jayeshseth.animations.navigation.NavGraph

/**
 * The main entry point for the iOS application.
 *
 * This function sets up the root view controller using Compose Multiplatform's
 * `ComposeUIViewController`. It initializes the navigation stack with a `rememberNavigator`,
 * sets the initial screen to `LandingRoutes.Home`, and wraps the entire UI
 * in the `AnimationsTheme`. The `NavGraph` composable is then used to handle
plemented navigation logic based on the provided back stack.
 *
 * @return A `UIViewController` that hosts the Compose UI.
 */
fun MainViewController() = ComposeUIViewController {
    val backStack = rememberNavigator(LandingRoutes.Home)
    AnimationsTheme {
        NavGraph(backStack) { }
    }
}