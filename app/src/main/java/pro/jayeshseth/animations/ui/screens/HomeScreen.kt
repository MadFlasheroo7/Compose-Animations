package pro.jayeshseth.animations.ui.screens

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.navigation.AnimationScreen
import pro.jayeshseth.animations.core.navigation.OnNavAction
import pro.jayeshseth.animations.core.ui.components.LazyIntrinsicGrid
import pro.jayeshseth.animations.core.ui.components.PrimaryInteractiveButton
import pro.jayeshseth.animations.core.ui.theme.syneFontFamily
import pro.jayeshseth.animations.core.utils.CAN_PLAY_SHADER
import pro.jayeshseth.animations.core.utils.collectPrefAsState
import pro.jayeshseth.animations.core.utils.commonPrefs
import pro.jayeshseth.animations.defaultApis.navigation.DefaultApisRoutes
import pro.jayeshseth.animations.easterEggs.navigation.EasterEggsRoutes
import pro.jayeshseth.animations.itemPlacements.navigation.ItemPlacementRoutes
import pro.jayeshseth.animations.navigation.NavDestinations
import pro.jayeshseth.animations.playground.navigation.PlaygroundRoutes
import pro.jayeshseth.animations.shaders.navigation.ShaderRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    hazeState: HazeState,
    color: Color,
    navAction: OnNavAction = {}
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current
    val updateTransition = updateTransition(color)
    val transitionColor by updateTransition.animateColor { it }
    var isInitialLoad by remember { mutableStateOf(true) }
    val lazyListState = rememberLazyListState()
    val prefs = context.commonPrefs
    val isUnlocked =
        prefs.collectPrefAsState(CAN_PLAY_SHADER, false)

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (lazyListState.isScrollInProgress) {
            isInitialLoad = false
        }
    }
    Log.d("HomeScreen", "isUnlocked: ${isUnlocked.value}")
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                ),
                title = {
                    Text(
                        text = "Animations",
                        fontSize = 35.sp,
                        fontWeight = FontWeight(750),
                        fontFamily = syneFontFamily
                    )
                }
            )
        },
    ) {
        LazyIntrinsicGrid(
            state = lazyListState,
            items = animationScreens(isUnlocked.value),
            columns = 2,
            contentPadding = it,
            span = { 2 },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { index, item ->
            AnimateButtonScale(
                index = index,
                text = item.title,
                hazeState = hazeState,
                isInitialLoad = isInitialLoad,
                flip = item.flip,
                onClick = {
                    navAction(item.route)
                }
            )
        }
    }
}

// temp
@Composable
private fun AnimateButtonScale(
    index: Int,
    text: String,
    hazeState: HazeState,
    onClick: () -> Unit,
    isInitialLoad: Boolean,
    flip: Boolean,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val animatedProgress = remember { Animatable(4.5f) }
    val animatedBlur = remember { Animatable(100f) }
    val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)
    var hasAnimated by remember { mutableStateOf(false) }
    LaunchedEffect(index) {
        if (!isInitialLoad || hasAnimated) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator!!.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK))
            }
            animatedProgress.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                )
            )
            hasAnimated = true
        } else {
            animatedProgress.snapTo(1f)
        }
    }
    LaunchedEffect(index) {
        animatedBlur.animateTo(
            targetValue = 0f,
            animationSpec = tween(300)
        )
    }
    PrimaryInteractiveButton(
        hazeState = hazeState,
        color = Color.White,
        flip = flip,
        text = text,
        onClick = onClick,
        scale = animatedProgress.value,
        blur = animatedBlur.value,
        modifier = modifier
    )
}


private fun animationScreens(isItUnlocked: Boolean): List<AnimationScreen> {
    return listOfNotNull(
        AnimationScreen(
            title = "Default Apis",
            route = DefaultApisRoutes.DefaultApisLanding
        ),
        AnimationScreen(
            title = "Playground",
            route = PlaygroundRoutes.PlaygroundLandingRoute
        ),
        AnimationScreen(
            title = "Item Placements",
            route = ItemPlacementRoutes.ListItemPlacementRoute
        ),
        AnimationScreen(
            title = "Shaders",
            route = ShaderRoutes.ShaderGraphRoute
        ),
        /*        AnimationScreen(
                    title = "Shapes & Morphing",
                    route = DefaultApisRoutes.AnimateVisibilityRoute
                ),*/
        /*        AnimationScreen(
                    title = "Text",
                    route = DefaultApisRoutes.AnimateVisibilityRoute
                ),*/
        AnimationScreen(
            title = "Canvas",
            route = DefaultApisRoutes.AnimateVisibilityRoute
        ),
        AnimationScreen(
            title = "Community",
            route = DefaultApisRoutes.AnimateVisibilityRoute
        ),
        AnimationScreen(
            title = "Past Easter Eggs",
            route = EasterEggsRoutes.EasterEggsLandingRoute
        ),
        /*        AnimationScreen(
                    title = "Community",
                    route = NavDestinations.Community.route
                ),*/
        if (isItUnlocked) AnimationScreen(
            title = "Master Customisations",
            route = NavDestinations.AboutScreen,
            flip = false // TODO fix inner shadow recomposition
        ) else null,
        AnimationScreen(
            title = "About",
            route = NavDestinations.AboutScreen
        )
    )
}