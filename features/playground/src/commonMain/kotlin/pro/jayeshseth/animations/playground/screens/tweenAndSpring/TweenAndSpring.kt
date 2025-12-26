package pro.jayeshseth.animations.playground.screens.tweenAndSpring

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.AnimationTabs
import pro.jayeshseth.animations.core.model.DampingRatioList
import pro.jayeshseth.animations.core.model.EasingList
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.model.StiffnessList
import pro.jayeshseth.animations.core.model.animationTabsList
import pro.jayeshseth.animations.core.ui.components.AnimatedChart
import pro.jayeshseth.animations.core.ui.components.AnimatedTab
import pro.jayeshseth.animations.core.ui.components.HazedSegmentedButton
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.core.ui.components.TabContent
import pro.jayeshseth.animations.core.ui.components.Toggler
import pro.jayeshseth.animations.playground.components.CodePreview
import pro.jayeshseth.animations.playground.components.PreviewGrid
import pro.jayeshseth.animations.playground.components.SpringOptions
import pro.jayeshseth.animations.playground.components.TweenOptions
import pro.jayeshseth.animations.playground.model.TweenAndSpringSpecState
import pro.jayeshseth.animations.playground.model.TweenNSpringSpec
import pro.jayeshseth.animations.playground.utils.BASE_FEATURE_ROUTE

enum class PlaygroundPreviewTabs { Chart, Preview }

@Composable
fun TweenAndSpringScreen(
    hazeState: HazeState,
    onClickLink: OnClickLink,
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan
) {
    val previewTabs = remember { PlaygroundPreviewTabs.entries }
    val specTabs = remember { TweenNSpringSpec.entries }
    val contentPagerState = rememberPagerState { previewTabs.size }
    val configsPagerState = rememberPagerState { animationTabsList().size }
    var selectedPreviewIndex by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope { Dispatchers.Default }
    val easingList = remember { EasingList }
    val selectedEasing by remember { mutableStateOf(easingList[0]) }
    val dampingRatioList = remember { DampingRatioList }
    val stiffnessList = remember { StiffnessList }
    val selectedDampingRatio by remember { mutableStateOf(dampingRatioList[0]) }
    val selectedStiffness by remember { mutableStateOf(stiffnessList[0]) }
    var replay by remember { mutableStateOf(false) }
    val state = remember {
        mutableStateOf(
            TweenAndSpringSpecState(
                specs = specTabs,
                selectedSpec = TweenNSpringSpec.Tween,
                durationMillis = 1000,
                delayMillis = 0,
                easingList = easingList,
                easing = selectedEasing,
                dampingRatioList = dampingRatioList,
                dampingRatio = selectedDampingRatio,
                stiffnessList = stiffnessList,
                stiffness = selectedStiffness,
                cubicA = 0.1f,
                cubicB = 0.2f,
                cubicC = 0.3f,
                cubicD = 0.4f,
            )
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.fillMaxSize()
    ) {
        Box(Modifier.weight(1f)) {
            HorizontalPager(
                state = contentPagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (previewTabs[page]) {
                    PlaygroundPreviewTabs.Chart -> {
                        AnimationChart(
                            state = state.value,
                            replay = replay,
                            animationType = state.value.selectedSpec
                        )
                    }

                    PlaygroundPreviewTabs.Preview -> {
                        PreviewGrid(
                            state.value,
                            replay,
                            Modifier
                                .padding(16.dp)
                                .safeContentPadding()
                        )
                    }
                }
            }
        }
        InteractiveButton(
            hazeState = hazeState,
            text = "Replay Animation",
            height = 45.dp,
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 12.dp),
            onClick = { replay = !replay }
        )

        TabContent(
            color = color,
            modifier = Modifier.weight(1f),
            hazeState = hazeState,
            tabsList = animationTabsList(),
            selectedIndex = configsPagerState.currentPage,
            tabComponent = { index, tab ->
                AnimatedTab(
                    isSelected = configsPagerState.currentPage == index,
                    onClick = {
                        scope.launch(Dispatchers.Main) {
                            configsPagerState.animateScrollToPage(index)
                        }
                    },
                ) {
                    Icon(
                        painter = painterResource(tab.icon),
                        contentDescription = tab.title,
                        tint = LocalContentColor.current
                    )
                }
            }
        ) {
            HorizontalPager(
                state = configsPagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (animationTabsList()[page]) {
                    AnimationTabs.Settings -> {
                        Configurations(
                            state = state.value,
                            hazeState = hazeState,
                            selectedIndex = selectedPreviewIndex,
                            onSpecChange = { selectedPreviewIndex = it },
                            onStateUpdate = { state.value = it }
                        )
                    }

                    AnimationTabs.Code -> {
                        CodePreview(hazeState, state.value)
                    }

                    AnimationTabs.Source -> {
                        LinksButtons(hazeState, onClickLink)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ShaderPreviewContent {
        TweenAndSpringScreen(
            hazeState = it,
            onClickLink = {}
        )
    }
}

@Preview
@Composable
private fun PreviewTabs() {

    var state by remember { mutableStateOf(0) }
    listOf("Tab 1", "Tab 2", "Tab 3")
    ShaderPreviewContent {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(500.dp)
                .hazeEffect(
                    state = it,
                    HazeStyle(
                        tint = HazeTint(Color.Black.copy(.4f)),
                        blurRadius = 50.dp,
                        noiseFactor = .5f,
                    )
                )
        ) {
            TabContent(
                hazeState = it,
                tabsList = animationTabsList(),
                selectedIndex = state,
                tabComponent = { index, tab ->
                    AnimatedTab(
                        isSelected = state == index,
                        onClick = { state = index },
                    ) {
                        Icon(
                            painter = painterResource(tab.icon),
                            contentDescription = tab.title,
                            tint = LocalContentColor.current
                        )
                    }
                },
                content = {}
            )
        }
    }
}

@Composable
private fun Configurations(
    hazeState: HazeState,
    state: TweenAndSpringSpecState,
    onStateUpdate: (TweenAndSpringSpecState) -> Unit,
    selectedIndex: Int,
    onSpecChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(
                WindowInsets(
                    left = WindowInsets.safeGestures.getLeft(
                        LocalDensity.current,
                        LayoutDirection.Ltr
                    ),
                    right = WindowInsets.safeGestures.getRight(
                        LocalDensity.current,
                        LayoutDirection.Ltr
                    ),
                )
            )
    ) {
        // TODO Build an alternative to SingleChoiceSegmentedButtonRow
        SingleChoiceSegmentedButtonRow(
            space = 2.dp,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            state.specs.forEachIndexed { index, label ->
                HazedSegmentedButton(
                    hazeState = hazeState,
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = state.specs.size
                    ),
                    onClick = {
                        onSpecChange(index)
                        onStateUpdate(
                            state.copy(
                                selectedSpec = label
                            )
                        )
                    },
                    selected = index == selectedIndex,
                ) {
                    Text(label.name, color = Color.White)
                }
            }
        }

        Toggler(
            title = "Apply Spec To Path",
            hazeState = hazeState,
            checked = state.applySpecToPath,
            onCheckedChanged = { onStateUpdate(state.copy(applySpecToPath = it)) },
            style = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Crossfade(
            targetState = selectedIndex,
            animationSpec = tween(550),
            label = "control switcher"
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                when (state.specs[it]) {
                    TweenNSpringSpec.Tween -> {
                        TweenOptions(
                            state = state,
                            hazeState = hazeState,
                            onStateUpdate = onStateUpdate,
                        )
                    }

                    TweenNSpringSpec.Spring -> {
                        SpringOptions(
                            state = state,
                            hazeState = hazeState,
                            onStateUpdate = onStateUpdate,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LinksButtons(
    hazeState: HazeState,
    onClickLink: OnClickLink,
) {
    val urlLauncher = LocalUriHandler.current
    val blog = "https://blog.realogs.in/animating-jetpack-compose-ui/"
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            InteractiveButton(
                text = "Blog",
                height = 70.dp,
                hazeState = hazeState,
                onClick = { urlLauncher.openUri(blog) }
            )
        }
        item {
            InteractiveButton(
                text = "Github",
                height = 70.dp,
                hazeState = hazeState,
                onClick = { onClickLink("$BASE_FEATURE_ROUTE/screens/tweenAndSpring/TweenAndSpring.kt") },
            )
        }
    }
}


// TODO Generalize chart
@Composable
private fun AnimationChart(
    state: TweenAndSpringSpecState,
    replay: Boolean,
    animationType: TweenNSpringSpec,
) {
    var points by remember { mutableStateOf(emptyList<Offset>()) }

    LaunchedEffect(replay, animationType, state) {
        when (animationType) {
            TweenNSpringSpec.Tween -> {
                val easing = if (state.useCustomEasing) {
                    CubicBezierEasing(state.cubicA, state.cubicB, state.cubicC, state.cubicD)
                } else {
                    state.easing.easing
                }
                points = (0..100).map {
                    val fraction = it / 100f
                    Offset(fraction, easing.transform(fraction))
                }
            }

            TweenNSpringSpec.Spring -> {
                val anim = TargetBasedAnimation(
                    animationSpec = spring(
                        dampingRatio = if (state.useCustomDampingRatioAndStiffness) state.customDampingRatio else state.dampingRatio.dampingRatio,
                        stiffness = if (state.useCustomDampingRatioAndStiffness) state.customStiffness else state.stiffness.stiffness
                    ),
                    typeConverter = Float.VectorConverter,
                    initialValue = 0f,
                    targetValue = 1f,
                )

                val durationNanos = anim.durationNanos
                if (durationNanos == 0L) {
                    points = emptyList()
                    return@LaunchedEffect
                }

                val numPoints = 100
                points = (0..numPoints).map { i ->
                    val playTimeNanos = i * (durationNanos / numPoints)
                    val value = anim.getValueFromNanos(playTimeNanos)
                    val fraction = playTimeNanos.toFloat() / durationNanos.toFloat()

                    Offset(fraction, value)
                }
            }
        }
    }

    val animationProgress = remember { Animatable(0f) }

    val easing by remember(state) {
        mutableStateOf(
            when {
                state.useCustomEasing.not() && state.applySpecToPath -> state.easing.easing
                state.useCustomEasing && state.applySpecToPath -> CubicBezierEasing(
                    state.cubicA,
                    state.cubicB,
                    state.cubicC,
                    state.cubicD
                )

                else -> FastOutSlowInEasing
            }
        )
    }
    val dampingRatio by remember {
        mutableFloatStateOf(
            when {
                state.applySpecToPath -> state.dampingRatio.dampingRatio
                state.useCustomDampingRatioAndStiffness -> state.customDampingRatio
                else -> state.dampingRatio.dampingRatio
            }
        )
    }
    val stiffness by remember {
        mutableFloatStateOf(
            when {
                state.applySpecToPath -> state.stiffness.stiffness
                state.useCustomDampingRatioAndStiffness -> state.customStiffness
                else -> state.stiffness.stiffness
            }
        )
    }
    LaunchedEffect(replay, points, state, animationType) {
        animationProgress.snapTo(0f)

        if (animationType == TweenNSpringSpec.Spring && state.applySpecToPath) {
            animationProgress.animateTo(
                1f,
                animationSpec = spring(
                    dampingRatio = dampingRatio,
                    stiffness = stiffness
                )
            )
        } else {
            animationProgress.animateTo(
                1f,
                animationSpec = tween(
                    durationMillis = state.durationMillis,
                    delayMillis = state.delayMillis,
                    easing = easing
                )
            )
        }
    }
    if (points.isNotEmpty()) {
        AnimatedChart(
            points,
            animationProgress.value,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .statusBarsPadding()
        )
    }
}