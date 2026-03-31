package pro.jayeshseth.animations.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.Navigator
import pro.jayeshseth.animations.core.navigation.Route
import pro.jayeshseth.animations.core.navigation.isSecondary
import pro.jayeshseth.animations.core.navigation.scenesAndStrategies.BasicTwoPaneScene
import pro.jayeshseth.animations.core.navigation.scenesAndStrategies.rememberBasicTwoPaneSceneStrategy
import pro.jayeshseth.animations.core.ui.backgrounds.BackgroundRegistrar
import pro.jayeshseth.animations.core.ui.components.BackgroundRenderer
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationState
import pro.jayeshseth.animations.defaultApis.navigation.DefaultApisRoutes
import pro.jayeshseth.animations.defaultApis.navigation.defaultApis
import pro.jayeshseth.animations.itemPlacements.navigation.ItemPlacementRoutes
import pro.jayeshseth.animations.itemPlacements.navigation.itemPlacements
import pro.jayeshseth.animations.masterCustomization.screens.MasterCustomization
import pro.jayeshseth.animations.navigation.navigation.NavigationRoutes
import pro.jayeshseth.animations.navigation.navigation.navigation
import pro.jayeshseth.animations.playground.navigation.PlaygroundRoutes
import pro.jayeshseth.animations.playground.navigation.playground
import pro.jayeshseth.animations.shaders.navigation.ShaderRoutes
import pro.jayeshseth.animations.shaders.navigation.shaders
import pro.jayeshseth.animations.ui.screens.AboutScreen
import pro.jayeshseth.animations.ui.screens.BouncyRope
import pro.jayeshseth.animations.ui.screens.HomeScreen

/**
 * Nav3 graph with custom animations and decorators
 */
@Composable
fun NavGraph(
    backStack: Navigator,
    onClickLink: OnClickLink
) {
    val hazeState = rememberHazeState()
//    val backStack = rememberNavigator(NavDestinations.Home)
//    val entryWithClippedBackgroundDecorator = navEntryDecorator<Any> { entry ->
//        Box(
//            Modifier
//                .clipToDeviceCornerRadius()
//        ) {
//            entry.Content()
//        }
//    }
//    var palette by remember { mutableStateOf<Palette?>(null) }

    val gl = rememberGraphicsLayer()
    var bitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }

    val customization = LocalCustomizationState.current
//    val accentColor = Color(customization.buttonAccentColorArgb)

//    val paletteColor by animateColorAsState(
//        targetValue =
//            palette?.lightVibrantSwatch?.toColor()
//            ?: palette?.lightMutedSwatch?.toColor()
//            ?: palette?.mutedSwatch?.toColor()
//            ?: palette?.vibrantSwatch?.toColor()
//            ?: palette?.dominantSwatch?.toColor()
//            ?: palette?.darkVibrantSwatch?.toColor()
//            ?: palette?.darkMutedSwatch?.toColor()
//            ?: Color.Cyan,
//    )
    LaunchedEffect(Unit) {
        LandingRoutes.register()
        DefaultApisRoutes.register()
        NavigationRoutes.register()
        PlaygroundRoutes.register()
        ItemPlacementRoutes.register()
        ShaderRoutes.register()
    }

    Box(
        Modifier.drawWithCache {
            onDrawWithContent {
                gl.record {
                    this@onDrawWithContent.drawContent()
                }
                drawLayer(gl)
            }
        }
    ) {
        BackgroundRenderer(
            backgroundType = customization.backgroundType,
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState)
                .blur(customization.backgroundBlur)
        )

//        bitmap?.let {
//            Image(
//                bitmap = it,
//                contentDescription = "",
//                modifier = Modifier.size(200.dp)
//            )
//        }
    }

    val basicTwoPaneSceneStrategy = rememberBasicTwoPaneSceneStrategy<Route>()

    LaunchedEffect(backStack.backStack.count()) {
        println("route backstack ${backStack.backStack.last().isSecondary}")
    }
    NavDisplay(
        backStack = backStack.backStack,
        onBack = { backStack.navBack() },
        sceneStrategy = basicTwoPaneSceneStrategy,
        transitionSpec = {
            // Slide in from right when navigating forward
            slideInHorizontally(
                initialOffsetX = { it },
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioLowBouncy,
//                    stiffness = Spring.StiffnessLow,
//                )
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { -it },
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioLowBouncy,
//                    stiffness = Spring.StiffnessLow,
//                )
            )
        },
        popTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
        },
        predictivePopTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
        },
//        modifier = Modifier
//            .clickable(
//                indication = null,
//                interactionSource = null
//            ) {
//                currentShaderIndex = (currentShaderIndex + 1) % shaders.size
//            },

//        entryDecorators = listOf(
//            entryWithClippedBackgroundDecorator,
//            rememberSceneSetupNavEntryDecorator(),
//            rememberSavedStateNavEntryDecorator()
//        ),
        entryProvider = entryProvider {
            entry<LandingRoutes.Home>(
                metadata = BasicTwoPaneScene.primaryPane()
            ) {
                HomeScreen(
                    hazeState = hazeState,
                    isSceneActivated = backStack.backStack.lastOrNull()?.isSecondary == true
                ) { route ->
                    backStack.navigate(route)
                }
            }
            // Feature Graphs
            defaultApis(onClickLink, hazeState) { backStack.navigate(it) }
            playground(
                hazeState = hazeState,
                onClickLink = onClickLink,
                onNavAction = { backStack.navigate(it) })
            itemPlacements(
                hazeState = hazeState,
                onClickLink = onClickLink,
                onNavAction = { backStack.navigate(it) })
            shaders(
                hazeState = hazeState,
                onClickLink = onClickLink,
                onNavAction = { backStack.navigate(it) })
//            easterEggs(hazeState = hazeState) { backStack.navigate(it) }
            navigation(hazeState = hazeState) //
            entry<LandingRoutes.BouncyRope> {
                BouncyRope()
            }
            entry<LandingRoutes.MasterCustomization> {
                MasterCustomization(hazeState)
            }
            entry<LandingRoutes.AboutScreen> {
                AboutScreen(hazeState)
            }
            // TODO add community
            entry<LandingRoutes.Community> {
//                AboutScreen(hazeState)
            }
        }
    )
}


fun registerAllRoutes() {
    LandingRoutes.register()
    DefaultApisRoutes.register()
    NavigationRoutes.register()
    PlaygroundRoutes.register()
    ItemPlacementRoutes.register()
    ShaderRoutes.register()
}

/**
 * One-time app initialization.
 * Registers all shaders and navigation routes.
 */
fun initializeApp() {
    BackgroundRegistrar.register()
    registerAllRoutes()
}
