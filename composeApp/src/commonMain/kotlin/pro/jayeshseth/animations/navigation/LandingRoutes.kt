package pro.jayeshseth.animations.navigation

import kotlinx.serialization.Serializable
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.core.navigation.RouteSerializer


/**
 * Defines the sealed object hierarchy for the main landing screens of the application.
 * Each object represents a distinct, serializable route that can be navigated to.
 *
 * This object also provides a [register] function to make these routes known to the
 * [RouteSerializer], enabling type-safe navigation and serialization.
 */
object LandingRoutes {
    @Serializable
    data object Home : Route

    @Serializable
    data object BouncyRope : Route

    @Serializable
    data object AboutScreen : Route

    @Serializable
    data object Community : Route

    /**
     * Registers all the routes defined within the [LandingRoutes] object.
     * This function should be called during application startup to ensure that these routes
     * are recognized by the navigation serialization system.
     */
    fun register() {
        RouteSerializer.register<Home>()
        RouteSerializer.register<BouncyRope>()
        RouteSerializer.register<AboutScreen>()
        RouteSerializer.register<Community>()
    }
}