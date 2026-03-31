package pro.jayeshseth.animations.shaders.screens.rainbowCircle

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.hypnoticcanvas.shaderBackground
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.AnimationTabs
import pro.jayeshseth.animations.core.model.Fields
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.model.lazyNavBarPadding
import pro.jayeshseth.animations.core.ui.components.AnimatedTab
import pro.jayeshseth.animations.core.ui.components.CodeBlockWithLineNumbers
import pro.jayeshseth.animations.core.ui.components.CopyIconButton
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.components.LocalSharedTransitionScope
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.core.ui.components.SliderTemplate
import pro.jayeshseth.animations.core.ui.components.TabContent
import pro.jayeshseth.animations.core.ui.components.TabsRow
import pro.jayeshseth.animations.core.ui.components.Toggler
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.core.ui.theme.syneFontFamily
import pro.jayeshseth.animations.core.ui.utils.currentDeviceConfiguration
import pro.jayeshseth.animations.shaders.utils.BASE_FEATURE_ROUTE

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RainbowCircle(
    hazeState: HazeState,
    onClickLink: OnClickLink,
    modifier: Modifier = Modifier
) {
    val deviceConfig = currentDeviceConfiguration()
    val isWideScreen = deviceConfig.isWideScreen

    val pagerState = rememberPagerState { AnimationTabs.Tabs.size }
    val scope = rememberCoroutineScope { Dispatchers.Default }

    var rainbowCircleState by remember {
        mutableStateOf(RainbowCircleState())
    }

    val shader = remember(rainbowCircleState.reduceDots) {
        RainbowCircleShader(reduceDots = rainbowCircleState.reduceDots)
    }

    val animatedPattern by animateFloatAsState(
        targetValue = rainbowCircleState.pattern,
        animationSpec = tween(1000)
    )

    val animatedExpansion by animateFloatAsState(
        targetValue = rainbowCircleState.expand,
        animationSpec = tween(1000)
    )

    val animatedBrightness by animateFloatAsState(
        targetValue = rainbowCircleState.brightness,
        animationSpec = tween(1000)
    )

    val animatedLayers by animateFloatAsState(
        targetValue = rainbowCircleState.layers,
        animationSpec = tween(1000)
    )

    val animatedSize by animateFloatAsState(
        targetValue = rainbowCircleState.size,
        animationSpec = tween(1000)
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = rainbowCircleState.alpha,
        animationSpec = tween(1000)
    )

    // Update shader uniforms from animated state.
    shader.updateUniforms(
        rainbowCircleState.copy(
            pattern = animatedPattern,
            expand = animatedExpansion,
            brightness = animatedBrightness,
            layers = animatedLayers,
            size = animatedSize,
            alpha = animatedAlpha
        )
    )

    val shaderContent = remember {
        movableContentOf {
            Box(
                Modifier
                    .fillMaxSize()
                    .shaderBackground(shader, rainbowCircleState.speed)
                    .padding(660.dp)
            )
        }
    }

    val tabsContent = remember {
        movableContentOf {
            TabContent(
                hazeState = hazeState,
                modifier = Modifier.fillMaxSize(),
                tabsList = AnimationTabs.Tabs,
                selectedIndex = pagerState.currentPage,
                tabComponent = { index, tab ->
                    AnimatedTab(
                        onClick = {
                            scope.launch(Dispatchers.Main) {
                                pagerState.animateScrollToPage(index)
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
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (AnimationTabs.Tabs[page]) {
                        AnimationTabs.Settings -> {
                            Configurations(
                                hazeState = hazeState,
                                rainbowCircleState = { rainbowCircleState },
                                onRainbowCircleStateChange = { rainbowCircleState = it }
                            )
                        }

                        AnimationTabs.Code -> {
                            CodePreview(hazeState, rainbowCircleState)
                        }

                        AnimationTabs.Source -> {
                            LinksButtons(hazeState, onClickLink)
                        }
                    }
                }
            }
        }
    }

    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this
        ) {
            AnimatedContent(
                targetState = isWideScreen,
                transitionSpec = {
                    fadeIn(tween(400)) togetherWith fadeOut(tween(400)) using
                            SizeTransform { _, _ ->
                                tween(400, easing = FastOutSlowInEasing)
                            }
                }
            ) { isWide ->
                if (isWide) {
                    Row(
                        modifier = modifier.fillMaxSize()
                    ) {
                        Box(
                            Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState("shader_bounds"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                )
                        ) {
                            shaderContent()
                        }
                        Box(
                            Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState("tabs_bounds"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                )
                        ) {
                            tabsContent()
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier.fillMaxSize()
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState("shader_bounds"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                )
                        ) {
                            shaderContent()
                        }
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState("tabs_bounds"),
                                    animatedVisibilityScope = this@AnimatedContent,
                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                )
                        ) {
                            tabsContent()
                        }
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
    val blog = "https://blog.realogs.in/composing-pixels/"
    val shaderSource =
        "https://shaders.skia.org/?id=2bee4488820c3253cd8861e85ce3cab86f482cfddd79cfd240591bf64f7bcc38"
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        InteractiveButton(
            hazeState = hazeState,
            text = "Blog",
            height = 70.dp,
            onClick = { urlLauncher.openUri(blog) }
        )
        InteractiveButton(
            hazeState = hazeState,
            text = "Shader Source",
            height = 70.dp,
            onClick = { urlLauncher.openUri(shaderSource) }
        )
        InteractiveButton(
            text = "Github",
            hazeState = hazeState,
            height = 70.dp,
            onClick = { onClickLink("$BASE_FEATURE_ROUTE/screens/rainbowCircle/RainbowCircle.kt") },
        )

    }
}

@Composable
private fun CodePreview(
    hazeState: HazeState,
    rainbowCircleState: RainbowCircleState,
    modifier: Modifier = Modifier
) {
    val tabsList = remember { mutableStateListOf("SKSL", "Compose") }
    val selectedTab = remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current
    val clipboardManagerr = LocalClipboard.current

    var code by remember(selectedTab.intValue) {
        mutableStateOf(
            if (selectedTab.intValue == 0) {
                RainbowShaderCode(rainbowCircleState).shaderCode
            } else {
                RainbowShaderCode(rainbowCircleState).composeCode
            }
        )
    }
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        stickyHeader {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                CopyIconButton(
                    hazeState = hazeState,
                    onClick = {
//                        scope.launch {
//                            clipboardManagerr.setClipEntry(
//                                clipEntry = ClipEntry(
//                                    clipData = ClipData(
//                                        ClipDescription(
//                                            "Rainbow Shader Code",
//                                            arrayOf("text/plain")
//                                        ),
//                                        ClipData.Item(code)
//                                    )
//                                )
//                            )
//                        }
                    },
                    modifier = Modifier.fillMaxHeight()
                )
                TabsRow(
                    tabsList = tabsList,
                    selectedIndex = selectedTab.intValue,
                    hazeState = hazeState,
                    modifier = Modifier.padding(start = 16.dp),
                    tabComponent = { index, tabTitle ->
                        AnimatedTab(
                            onClick = {
                                selectedTab.intValue = index
                            },
                        ) {
                            Text(tabTitle, overflow = TextOverflow.Ellipsis, maxLines = 1)
                        }
                    }
                )
            }
        }

        item {
            val text = code.split("\n")
            CodeBlockWithLineNumbers(
                text, modifier = Modifier
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
            )
        }

        lazyNavBarPadding()
    }

//    Rebugger(
//        composableName = "code preview",
//        trackMap = mapOf(
//            "code" to code,
//            "selectedTab" to selectedTab.intValue,
//            "rainbowCircleState" to rainbowCircleState,
//        )
//    )
}

@Composable
private fun Configurations(
    hazeState: HazeState,
    rainbowCircleState: () -> RainbowCircleState,
    onRainbowCircleStateChange: (RainbowCircleState) -> Unit
) {
    val sliderData = slidersList(rainbowCircleState()) { onRainbowCircleStateChange(it) }

    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 10.dp
        ),
        modifier = Modifier
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
        items(sliderData) { data ->
            when (data) {
                is Fields.SliderData -> {
                    SliderTemplate(
                        title = data.title,
                        hazeState = hazeState,
                        value = { data.value },
                        step = { data.step },
                        onValueChange = { value -> data.onValueChange(value) },
                        valueRange = data.valueRange,
                        roundToInt = data.roundToInt,
                        titlePadding = PaddingValues(vertical = 10.dp),
                        onIncrement = {
                            data.onValueChange(data.value + .1f)
                        },
                        onDecrement = {
                            data.onValueChange(data.value - .1f)
                        },
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = syneFontFamily()
                        )
                    )
                }

                is Fields.ToggleData -> {
                    Toggler(
                        hazeState = hazeState,
                        title = data.title,
                        checked = data.value,
                        onCheckedChanged = data.onValueChange,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = syneFontFamily()
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }
        lazyNavBarPadding()
    }
}

@Stable
private fun slidersList(
    sliderData: RainbowCircleState,
    onSliderDataChange: (RainbowCircleState) -> Unit
): List<Fields> {
    return listOf(
        Fields.ToggleData(
            title = "Reduce Dots",
            value = sliderData.reduceDots,
            onValueChange = {
                onSliderDataChange(
                    RainbowCircleState(
                        layers = if (it) 100.0f else 200.0f,
                        reduceDots = it
                    )
                )
            }
        ),
        Fields.SliderData(
            title = "Pattern",
            value = sliderData.pattern,
            onValueChange = {
                onSliderDataChange(sliderData.copy(pattern = it))
            },
            valueRange = 0f..10f,
        ),
        Fields.SliderData(
            title = "Expand",
            value = sliderData.expand,
            onValueChange = {
                onSliderDataChange(sliderData.copy(expand = it))
            },
            valueRange = 0f..10f,
        ),
        Fields.SliderData(
            title = "Speed",
            value = sliderData.speed,
            onValueChange = {
                onSliderDataChange(sliderData.copy(speed = it))
            },
            valueRange = -1f..1f,
        ),
        Fields.SliderData(
            title = "Size",
            value = sliderData.size,
            onValueChange = {
                onSliderDataChange(sliderData.copy(size = it))
            },
            valueRange = -10f..10f,
        ),
        Fields.SliderData(
            title = "Red",
            value = sliderData.red,
            onValueChange = {
                onSliderDataChange(sliderData.copy(red = it))
            },
            valueRange = -10f..10f,
        ),
        Fields.SliderData(
            title = "Green",
            value = sliderData.green,
            onValueChange = {
                onSliderDataChange(sliderData.copy(green = it))
            },
            valueRange = -10f..10f,
        ),
        Fields.SliderData(
            title = "Blue",
            value = sliderData.blue,
            onValueChange = {
                onSliderDataChange(sliderData.copy(blue = it))
            },
            valueRange = -10f..10f,
        ),
        Fields.SliderData(
            title = "Alpha",
            value = sliderData.alpha,
            onValueChange = {
                onSliderDataChange(sliderData.copy(alpha = it))
            },
            valueRange = -1f..1f,
        ),
        Fields.SliderData(
            title = "Layers",
            value = sliderData.layers,
            onValueChange = {
                onSliderDataChange(sliderData.copy(layers = it))
            },
            valueRange = -0f..3000f,
        ),
        Fields.SliderData(
            title = "Brightness",
            value = sliderData.brightness,
            onValueChange = {
                onSliderDataChange(sliderData.copy(brightness = it))
            },
            valueRange = -100f..40000f,
        ),
    )
}

@Preview
@Composable
private fun PreviewRainbowCircle() {
    AnimationsTheme {
        ShaderPreviewContent {
            RainbowCircle(it, {})
        }
    }
}