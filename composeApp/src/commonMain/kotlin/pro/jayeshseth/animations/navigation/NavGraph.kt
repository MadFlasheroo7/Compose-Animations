package pro.jayeshseth.animations.navigation

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.BlackCherryCosmos
import com.mikepenz.hypnoticcanvas.shaders.BubbleRings
import com.mikepenz.hypnoticcanvas.shaders.GoldenMagma
import com.mikepenz.hypnoticcanvas.shaders.GradientFlow
import com.mikepenz.hypnoticcanvas.shaders.Heat
import com.mikepenz.hypnoticcanvas.shaders.IceReflection
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import com.mikepenz.hypnoticcanvas.shaders.OilFlow
import com.mikepenz.hypnoticcanvas.shaders.PurpleLiquid
import com.mikepenz.hypnoticcanvas.shaders.Stage
import com.mikepenz.hypnoticcanvas.shaders.Stripy
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.navigation.Navigator
import pro.jayeshseth.animations.core.navigation.RoutePolymorphicSerializer
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.defaultApis.navigation.DefaultApisRoutes
import pro.jayeshseth.animations.defaultApis.navigation.DefaultApisRoutes.DefaultApisLanding
import pro.jayeshseth.animations.defaultApis.navigation.defaultApis
import pro.jayeshseth.animations.itemPlacements.navigation.itemPlacements
import pro.jayeshseth.animations.navigation.navigation.navigation
import pro.jayeshseth.animations.playground.navigation.playground
import pro.jayeshseth.animations.shaders.navigation.shaders
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

    val shaders = remember {
        listOf(
            InkFlow,
            BlackCherryCosmos,
            OilFlow,
            Heat(),
            GoldenMagma,
            BubbleRings,
            Stage,
            IceReflection,
            GradientFlow,
            PurpleLiquid,
            Stripy()
        )
    }
    var currentShaderIndex by remember { mutableStateOf(0) }

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
    LaunchedEffect(currentShaderIndex) {
//        bitmap = gl.toImageBitmap()
    }
//
//    LaunchedEffect(bitmap, currentShaderIndex) {
//        palette = withContext(Dispatchers.Default) {
//            if (bitmap != null) {
//                val sb = bitmap!!.asAndroidBitmap().copy(
//                    Bitmap.Config.ARGB_8888,
//                    false
//                )
//                Palette.from(sb)
//                    .generate()
//            } else null
//        }
//    }

    LaunchedEffect(Unit) {
        LandingRoutes.register()
        DefaultApisRoutes.register()
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
        Box(
            Modifier
                .fillMaxSize()
                .hazeSource(hazeState)
                .blur(12.dp)
                .shaderBackground(shaders[currentShaderIndex], .2f)
        )

//        bitmap?.let {
//            Image(
//                bitmap = it,
//                contentDescription = "",
//                modifier = Modifier.size(200.dp)
//            )
//        }
    }
    NavDisplay(
        backStack = backStack.backStack,
        onBack = { backStack.navBack() },
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = null
            ) {
                currentShaderIndex = (currentShaderIndex + 1) % shaders.size
            },
//        entryDecorators = listOf(
//            entryWithClippedBackgroundDecorator,
//            rememberSceneSetupNavEntryDecorator(),
//            rememberSavedStateNavEntryDecorator()
//        ),
        entryProvider = entryProvider {
            entry<LandingRoutes.Home> {
                HomeScreen(
//                    color = paletteColor,
                    color = Color.Cyan,
                    hazeState = hazeState
                ) { route ->
                    backStack.navigate(route)
                }
            }
            // Feature Graphs
            defaultApis(onClickLink, hazeState, color = Color.Cyan) { backStack.navigate(it) }
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
            entry<LandingRoutes.AboutScreen> {
//                AboutScreen(hazeState)
            }
            // TODO add community
            entry<LandingRoutes.Community> {
//                AboutScreen(hazeState)
            }
        }
    )
}