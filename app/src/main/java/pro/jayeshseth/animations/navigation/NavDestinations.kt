package pro.jayeshseth.animations.navigation

sealed class NavDestinations(val route: String) {
    data object Home : NavDestinations("HOME_SCREEN")
    data object AnimateVisibility : NavDestinations("ANIMATE_VISIBILITY")
    data object AnimateContent : NavDestinations("ANIMATE_CONTENT")
    data object AnimateGesture : NavDestinations("ANIMATE_GESTURES")
    data object AnimateNavGraph : NavDestinations("ANIMATED_NAV_GRAPH")
    data object InfiniteRotation : NavDestinations("INFINITE_ROTATION")
    data object SwipeRefresh : NavDestinations("SWIPE_REFRESH")
    data object BouncyRope : NavDestinations("BOUNCY_ROPE")
    data object AnimateValueAsState : NavDestinations("ANIMATE_VALUE_AS_STATE")
    data object AnimatedListItemPlacement : NavDestinations("ITEM_PLACEMENT_ANIMATION")
    data object TrippyBlinders : NavDestinations("TRIPPY_BLINDERS")
    data object AboutScreen : NavDestinations("ABOUT_SCREEN")
    data object SlideInOut : NavDestinations("SLIDE_IN_OUT")
    data object ScaleItemPlacement : NavDestinations("SCALE_ITEM_PLACEMENT")
}