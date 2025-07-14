package pro.jayeshseth.animations.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Holds all the destinations used in this app
 */
//TODO multi module?
sealed class NavDestinations : NavKey {
    @Serializable data object Home : NavDestinations()
    @Serializable data object AnimateVisibility : NavDestinations()
    @Serializable data object AnimateContent : NavDestinations()
    @Serializable data object AnimateGesture : NavDestinations()
    @Serializable data object AnimateNavGraph : NavDestinations()
    @Serializable data object InfiniteRotation : NavDestinations()
    @Serializable data object SwipeRefresh : NavDestinations()
    @Serializable data object BouncyRope : NavDestinations()
    @Serializable data object AnimateValueAsState : NavDestinations()
    @Serializable data object AnimatedListItemPlacement : NavDestinations()
    @Serializable data object TrippyBlinders : NavDestinations()
    @Serializable data object AboutScreen : NavDestinations()
    @Serializable data object SlideInOut : NavDestinations()
    @Serializable data object ScaleItemPlacement : NavDestinations()
    @Serializable data object FadeItemPlacement : NavDestinations()
    @Serializable data object Community : NavDestinations()
    @Serializable data object PastEasterEggs : NavDestinations()
    @Serializable data object PhysicsLayoutAboutScreen : NavDestinations()
    @Serializable data object Shaders : NavDestinations()
    @Serializable data object RainbowCircleShader : NavDestinations()
    @Serializable data object InterstellarShader : NavDestinations()
    @Serializable data object Playground : NavDestinations()
    @Serializable data object TweenAndSpring : NavDestinations()
}