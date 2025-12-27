package pro.jayeshseth.animations.navigation.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.core.navigation.RouteSerializer

object NavigationRoutes {

    @Serializable
    data object Nav2GraphRoute : Route()

    fun register() {
        RouteSerializer.register<Nav2GraphRoute>()
    }
}
