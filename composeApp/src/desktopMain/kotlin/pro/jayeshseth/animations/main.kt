package pro.jayeshseth.animations

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.navigation.LandingRoutes
import pro.jayeshseth.animations.navigation.NavGraph

/**
 * The main entry point of the Compose Animations application.
 *
 * This function sets up the main application window, initializes the navigation back stack,
 * and configures global key events. It establishes the root of the UI, wrapping it with
 * the [AnimationsTheme] and setting up the [NavGraph] for navigation.
 *
 * A key binding is set up to handle back navigation: pressing `Alt + Left Arrow`
 * will trigger a `navBack()` action on the navigator.
 */
fun main() = application {
    val backStack = rememberNavigator(LandingRoutes.Home)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Animations",
        onKeyEvent = { keyEvent ->
            if (keyEvent.type == KeyEventType.KeyDown &&
                keyEvent.key == Key.DirectionLeft &&
                keyEvent.isAltPressed
            ) {
                backStack.navBack()
                true
            } else {
                false
            }
        },
    ) {
        AnimationsTheme {
            NavGraph(backStack) { }
        }
    }
}