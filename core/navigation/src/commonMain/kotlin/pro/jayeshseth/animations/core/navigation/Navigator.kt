package pro.jayeshseth.animations.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.savedState
import androidx.savedstate.serialization.decodeFromSavedState
import androidx.savedstate.serialization.encodeToSavedState
import pro.jayeshseth.animations.core.navigation.Navigator.Companion.Saver

class Navigator(private var startRoute: Route,val onNavigate: (Route) -> Unit = {}) {
    val backStack = mutableStateListOf(startRoute)

    /**
     * Navigate to the given route.
     */
    fun navigate(route: Route) {
        onNavigate(route)
        backStack.add(route)
    }


    /**
     * Navigate back to the previous route.
     */
    fun navBack() {
        if (backStack.size <= 1) {
            return
        }
        backStack.removeLastOrNull()
    }

    companion object {
        private const val KEY_START_ROUTE = "start_route"
        private const val KEY_BACK_STACK = "back_stack"
        private val routeSerializer = RouteSerializer()

        /**
         * A [Saver] for the [Navigator] class.
         *
         * This saver is responsible for saving and restoring the state of the navigator
         * across configuration changes or process death. It saves the start route and the
         * entire back stack.
         *
         * The state is saved into a [SavedState] bundle.
         * - `save`: Serializes the `startRoute` and the `backStack` into the bundle.
         * - `restore`: Deserializes the `startRoute` and `backStack` from the bundle
         *   and reconstructs the [Navigator] instance.
         */
        val Saver = Saver<Navigator, SavedState>(
            save = { navigator ->
                var savedState: SavedState = savedState {
                    putSavedState(
                        KEY_START_ROUTE,
                        encodeToSavedState(routeSerializer, navigator.startRoute)
                    )
                    putSavedStateList(
                        KEY_BACK_STACK,
                        navigator.backStack.map { encodeToSavedState(routeSerializer, it) }
                    )
                }
                savedState
            },
            restore = { savedState ->
                savedState.read {
                    val startRoute =
                        decodeFromSavedState(routeSerializer, getSavedState(KEY_START_ROUTE))
                    val navigator = Navigator(startRoute)

                    val restoredStack = getSavedStateListOrNull(KEY_BACK_STACK)?.map {
                        decodeFromSavedState(routeSerializer, it)
                    }

                    if (!restoredStack.isNullOrEmpty()) {
                        navigator.backStack.clear()
                        navigator.backStack.addAll(restoredStack)
                    }
                    println("route backstack ${navigator.backStack}") // TODO replace with a kmp logger
                    navigator
                }
            }
        )
    }
}

/**
 * Creates a [Navigator] that is remembered across compositions and survives process death and
 * configuration changes.
 *
 * This composable uses [rememberSaveable] with a custom [Navigator.Saver] to ensure that the
 * navigation state, including the back stack, is preserved.
 *
 * @param startRoute The initial route to be displayed. This will be the first entry in the
 *   navigation back stack.
 * @return A remembered instance of [Navigator].
 */
@Composable
fun rememberNavigator(startRoute: Route, onNavigate: (Route) -> Unit = {}) = rememberSaveable(saver = Navigator.Saver) {
    Navigator(startRoute, onNavigate)
}
