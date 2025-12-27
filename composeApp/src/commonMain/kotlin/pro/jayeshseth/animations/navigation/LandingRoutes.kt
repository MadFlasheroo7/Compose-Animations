package pro.jayeshseth.animations.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.core.navigation.RouteSerializer


object LandingRoutes {
    @Serializable
    data object Home : Route()

    @Serializable
    data object BouncyRope : Route()

    @Serializable
    data object AboutScreen : Route()

    @Serializable
    data object Community : Route()

    fun register() {
        RouteSerializer.register<Home>()
        RouteSerializer.register<BouncyRope>()
        RouteSerializer.register<AboutScreen>()
        RouteSerializer.register<Community>()
    }
}