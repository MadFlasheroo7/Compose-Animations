package pro.jayeshseth.animations.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.navEntryDecorator
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.BlackCherryCosmos
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.modifiers.clipToDeviceCornerRadius
import pro.jayeshseth.animations.defaultApis.navigation.defaultApis
import pro.jayeshseth.animations.easterEggs.navigation.easterEggs
import pro.jayeshseth.animations.itemPlacements.navigation.itemPlacements
import pro.jayeshseth.animations.navigation.navigation.navigation
import pro.jayeshseth.animations.playground.navigation.playground
import pro.jayeshseth.animations.shaders.navigation.shaders
import pro.jayeshseth.animations.ui.screens.AboutScreen
import pro.jayeshseth.animations.ui.screens.BouncyRope
import pro.jayeshseth.animations.ui.screens.HomeScreen

/**
 * Nav3 graph with custom animations and decorators
 */
@Composable
fun NavGraph(onClickLink: OnClickLink) {
    val backStack = rememberNavigator(NavDestinations.Home)
    val entryWithClippedBackgroundDecorator = navEntryDecorator<Any> { entry ->
        Box(
            Modifier
                .clipToDeviceCornerRadius()
                .shaderBackground(BlackCherryCosmos, speed = 0.2f)
//                .background(MaterialTheme.colorScheme.background)
        ) {
            entry.Content()
        }
    }
    NavDisplay(
        backStack = backStack.backStack,
        onBack = { backStack.navBack() },
        entryDecorators = listOf(
            entryWithClippedBackgroundDecorator,
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<NavDestinations.Home> {
                HomeScreen { route ->
                    backStack.navigate(route)
                }
            }
            // Feature Graphs
            defaultApis(onClickLink)
            playground(onClickLink = onClickLink, onNavAction = { backStack.navigate(it) })
            itemPlacements(onClickLink = onClickLink, onNavAction = { backStack.navigate(it) })
            shaders(onClickLink = onClickLink, onNavAction = { backStack.navigate(it) })
            easterEggs { backStack.navigate(it) }
            navigation()

            entry<NavDestinations.BouncyRope> {
                BouncyRope()
            }
            entry<NavDestinations.AboutScreen> {
                AboutScreen()
            }
            // TODO add community
            entry<NavDestinations.Community> {
                AboutScreen()
            }
        }
    )
}