package pro.jayeshseth.animations.easterEggs.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route

object EasterEggsRoutes {

    @Serializable
    data object EasterEggsLandingRoute : Route()

    @Serializable
    data object PhysicsLayoutAboutRoute : Route()
}
