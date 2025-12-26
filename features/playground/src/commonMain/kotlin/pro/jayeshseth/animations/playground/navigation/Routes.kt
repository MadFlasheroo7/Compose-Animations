package pro.jayeshseth.animations.playground.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route

object PlaygroundRoutes {
    @Serializable
    data object PlaygroundLandingRoute : Route()

    @Serializable
    data object AnimationSpecRoute : Route()

    @Serializable
    data object TweenAndSpringRoute : Route()
}
