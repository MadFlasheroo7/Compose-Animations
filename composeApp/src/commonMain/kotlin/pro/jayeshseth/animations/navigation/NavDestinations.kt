package pro.jayeshseth.animations.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.core.navigation.RoutePolymorphicSerializer


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
        RoutePolymorphicSerializer.register<Home>()
        RoutePolymorphicSerializer.register<BouncyRope>()
        RoutePolymorphicSerializer.register<AboutScreen>()
        RoutePolymorphicSerializer.register<Community>()
    }
}