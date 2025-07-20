package pro.jayeshseth.animations.util

import pro.jayeshseth.animations.navigation.NavDestinations

typealias ComposeFriendlyFloat = () -> Float
typealias ComposeFriendlyInt = () -> Int
typealias OnClickLink = (path: String) -> Unit
typealias OnNavAction = (NavDestinations) -> Unit
