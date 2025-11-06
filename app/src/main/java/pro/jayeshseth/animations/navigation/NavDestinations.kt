package pro.jayeshseth.animations.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route


/**
 * Holds all the destinations used in this app
 */
//TODO multi module?
sealed class NavDestinations : Route() {
    @Serializable data object Home : NavDestinations()
    @Serializable data object BouncyRope : NavDestinations()
    @Serializable data object AboutScreen : NavDestinations()
    @Serializable data object Community : NavDestinations()
}