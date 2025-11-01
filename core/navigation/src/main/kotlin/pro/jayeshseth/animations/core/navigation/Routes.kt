package pro.jayeshseth.animations.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    sealed class Primary : Route()
    sealed class Shared : Route()
}