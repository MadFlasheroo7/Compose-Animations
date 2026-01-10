package pro.jayeshseth.animations

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.browser.window
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.navigation.LandingRoutes
import pro.jayeshseth.animations.navigation.NavGraph
import kotlin.js.ExperimentalWasmJsInterop

/**
 * The main entry point for the Compose for Web application.
 *
 * This function sets up the root [ComposeViewport] and initializes the navigation system.
 * It uses a `rememberNavigator` to manage the navigation stack, starting with `LandingRoutes.Home`.
 *
 * Browser history integration is handled here:
 * - When navigating to a new route, `window.history.pushState` is called to update the browser's URL,
 *   enabling deep linking and history.
 * - The `window.onpopstate` event (triggered by browser back/forward buttons) is intercepted
 *   to call `navBack()` on the navigator, keeping the app's state in sync with the browser's history.
 *
 * The entire UI is wrapped in the [AnimationsTheme] to apply consistent styling.
 * The [NavGraph] composable is then used to define the different screens and their transitions
 * based on the current state of the navigator.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalWasmJsInterop::class)
fun main() {
    ComposeViewport {
        // TODO temp test
        val backStack = rememberNavigator(
            LandingRoutes.Home,
            onNavigate = {route->
                window.history.pushState(null, "", route.toString())
                println("onNavigate $route")
            }
        )
        window.onpopstate = {
            it.preventDefault()
            backStack.navBack()
        }
        AnimationsTheme {
            NavGraph(backStack) { }
        }
    }
}