package pro.jayeshseth.animations.navigation.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.navigation.screens.nav2.AnimatedNavGraph

fun EntryProviderBuilder<Route>.navigation() {
    entry<NavigationRoutes.Nav2GraphRoute> {
        AnimatedNavGraph()
    }

}