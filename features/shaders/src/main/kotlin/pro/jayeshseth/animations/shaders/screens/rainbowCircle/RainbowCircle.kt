package pro.jayeshseth.animations.shaders.screens.rainbowCircle

import android.content.ClipData
import android.content.ClipDescription
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.theapache64.rebugger.Rebugger
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pro.jayeshseth.animations.core.model.AnimationTabs
import pro.jayeshseth.animations.core.model.Fields
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.model.animationTabsList
import pro.jayeshseth.animations.core.model.lazyNavBarPadding
import pro.jayeshseth.animations.core.ui.components.AnimatedTab
import pro.jayeshseth.animations.core.ui.components.CodeBlockWithLineNumbers
import pro.jayeshseth.animations.core.ui.components.CopyIconButton
import pro.jayeshseth.animations.core.ui.components.FeatureUnavailableScreen
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.components.SliderTemplate
import pro.jayeshseth.animations.core.ui.components.TabsRow
import pro.jayeshseth.animations.core.ui.components.Toggler
import pro.jayeshseth.animations.shaders.utils.BASE_FEATURE_ROUTE

@Composable
fun RainbowCircle(
    hazeState: HazeState,
    onClickLink: OnClickLink,
    modifier: Modifier = Modifier
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        InnerRainbowCircle(hazeState, onClickLink, modifier)
    } else {
        FeatureUnavailableScreen("Feature Unavailable for api below ${Build.VERSION_CODES.TIRAMISU}")
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun InnerRainbowCircle(
    hazeState: HazeState,
    onClickLink: OnClickLink,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { animationTabsList().size }
    val scope = rememberCoroutineScope { Dispatchers.Default }
    val time = remember { mutableFloatStateOf(0f) }
    var iterationLevel by remember { mutableStateOf(ShaderIterationLevel.LOW) }
    val rainbowCircleShader = remember(iterationLevel) {
        Log.d("RainbowCircle", "Recreating RainbowCircleShader with level: $iterationLevel")
        RainbowCircleShader(iterationLevel)
    }
    val shaderBrush =
        remember(iterationLevel) { derivedStateOf { ShaderBrush(rainbowCircleShader) } }

    val anim by remember { mutableStateOf(Animatable(200f)) }

    LaunchedEffect(iterationLevel) {
        anim.animateTo(
            targetValue = if (iterationLevel == ShaderIterationLevel.HIGH) 200.0f else 100.0f,
            animationSpec = tween(1000)
        )
    }

    val layers by animateFloatAsState(
        targetValue = if (iterationLevel == ShaderIterationLevel.HIGH) 200.0f else 100.0f,
        animationSpec = tween(1000)
    )

    val rainbowCircleState = remember(shaderBrush) {
        mutableStateOf(
            RainbowCircleState(
                size = 2f,
                red = 0f,
                blue = 4.0f,
                green = 2.0f,
                alpha = 1.0f,
                speed = 0.05f,
                layers = if (iterationLevel == ShaderIterationLevel.HIGH) 200.0f else 100.0f,
                brightness = 30000.0f,
                pattern = 5.3f,
                expand = 1.0f,
                reduceDots = iterationLevel == ShaderIterationLevel.LOW
            )
        )
    }

    val animatedPattern by animateFloatAsState(
        targetValue = rainbowCircleState.value.pattern,
        animationSpec = tween(1000)
    )

    val animatedExpansion by animateFloatAsState(
        targetValue = rainbowCircleState.value.expand,
        animationSpec = tween(1000)
    )

    val animatedBrightness by animateFloatAsState(
        targetValue = rainbowCircleState.value.brightness,
        animationSpec = tween(1000)
    )

    val animatedLayers by animateFloatAsState(
        targetValue = rainbowCircleState.value.layers,
        animationSpec = tween(1000)
    )

    val animatedSize by animateFloatAsState(
        targetValue = rainbowCircleState.value.size,
        animationSpec = tween(1000)
    )

    val animatedSpeed by animateFloatAsState(
        targetValue = rainbowCircleState.value.speed,
        animationSpec = tween(1000)
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = rainbowCircleState.value.alpha,
        animationSpec = tween(1000)
    )

    LaunchedEffect(Unit, iterationLevel) {
        while (true) {
            time.floatValue += rainbowCircleState.value.speed
            delay(100)
            Log.d("time", "time:${time.floatValue}\nspeed: ${rainbowCircleState.value.speed}")
        }
    }

    LaunchedEffect(rainbowCircleState.value.reduceDots) {
        iterationLevel = if (rainbowCircleState.value.reduceDots) {
            ShaderIterationLevel.LOW
        } else {
            ShaderIterationLevel.HIGH
        }
        Log.d("iteration", "${rainbowCircleShader.toString()}")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .weight(1f)
                .then(
                    if (isSystemInDarkTheme().not()) {
                        Modifier.background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.Black,
                                    Color.Black,
                                    MaterialTheme.colorScheme.background,
                                ),
                            )
                        )
                    } else {
                        Modifier
                    }
                )
                .drawWithCache {
                    onDrawBehind {
                        rainbowCircleShader.updateTime({ time.floatValue })
                        rainbowCircleShader.updatePattern({ animatedPattern })
                        rainbowCircleShader.updateExpand({ animatedExpansion })
                        rainbowCircleShader.updateBrightness({ animatedBrightness })
                        rainbowCircleShader.updateLayers({ animatedLayers })
                        rainbowCircleShader.updateSize({ animatedSize })
                        rainbowCircleShader.updateResolution(Size(size.width, size.height))
                        rainbowCircleShader.updateColor(
                            { rainbowCircleState.value.red },
                            { rainbowCircleState.value.green },
                            { rainbowCircleState.value.blue },
                            { animatedAlpha }
                        )
                        drawRect(shaderBrush.value)
                    }
                }
        )

        Column(
            Modifier
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .weight(1f)
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            TabsRow(
                tabsList = animationTabsList(),
                selectedIndex = pagerState.currentPage,
                hazeState = hazeState,
                tabComponent = { index, tab ->
                    AnimatedTab(
                        isSelected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch(Dispatchers.Main) {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = tab.icon),
                            contentDescription = tab.title,
                            tint = LocalContentColor.current
                        )
                    }
                }
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (animationTabsList()[page]) {
                    AnimationTabs.Settings -> {
                        Configurations(
                            hazeState = hazeState,
                            rainbowCircleState = { rainbowCircleState.value },
                            onRainbowCircleStateChange = { rainbowCircleState.value = it }
                        )
                    }

                    AnimationTabs.Code -> {
                        CodePreview(hazeState, iterationLevel, rainbowCircleState.value)
                    }

                    AnimationTabs.Source -> {
                        LinksButtons(hazeState, onClickLink)
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
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
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
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun CodePreview(
    hazeState: HazeState,
    iterationLevel: ShaderIterationLevel,
    rainbowCircleState: RainbowCircleState,
    modifier: Modifier = Modifier
) {
    val tabsList = remember { mutableStateListOf("AGSL", "Compose") }
    val selectedTab = remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current
    val clipboardManagerr = LocalClipboard.current

    var code by remember(selectedTab.intValue) {
        mutableStateOf(
            if (selectedTab.intValue == 0) {
                RainbowShaderCode(rainbowCircleState, iterationLevel).shaderCode()
            } else {
                RainbowShaderCode(rainbowCircleState, iterationLevel).composeCode()
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
                        scope.launch {
                            clipboardManagerr.setClipEntry(
                                clipEntry = ClipEntry(
                                    clipData = ClipData(
                                        ClipDescription(
                                            "Rainbow Shader Code",
                                            arrayOf("text/plain")
                                        ),
                                        ClipData.Item(code)
                                    )
                                )
                            )
                        }
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
                            isSelected = selectedTab.intValue == index,
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

    Rebugger(
        composableName = "code preview",
        trackMap = mapOf(
            "code" to code,
            "selectedTab" to selectedTab.intValue,
            "rainbowCircleState" to rainbowCircleState,
        )
    )
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
                            fontWeight = FontWeight.Bold
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
                            fontWeight = FontWeight.Bold
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
                onSliderDataChange(sliderData.copy(reduceDots = it))
            }
        ),
        Fields.SliderData(
            title = "Pattern",
            value = sliderData.pattern,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(pattern = it))
            },
            valueRange = 0f..10f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Expand",
            value = sliderData.expand,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(expand = it))
            },
            valueRange = 0f..10f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Speed",
            value = sliderData.speed,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(speed = it))
            },
            valueRange = -1f..1f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Size",
            value = sliderData.size,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(size = it))
            },
            valueRange = -10f..10f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Red",
            value = sliderData.red,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(red = it))
            },
            valueRange = -10f..10f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Green",
            value = sliderData.green,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(green = it))
            },
            valueRange = -10f..10f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Blue",
            value = sliderData.blue,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(blue = it))
            },
            valueRange = -10f..10f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Alpha",
            value = sliderData.alpha,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(alpha = it))
            },
            valueRange = -1f..1f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Layers",
            value = sliderData.layers,
            step = 1,
            onValueChange = {
                onSliderDataChange(sliderData.copy(layers = it))
            },
            valueRange = -0f..3000f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Brightness",
            value = sliderData.brightness,
            step = 0,
            onValueChange = {
                onSliderDataChange(sliderData.copy(brightness = it))
            },
            valueRange = -100f..40000f,
            roundToInt = false
        ),
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@PreviewDynamicColors
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun PreviewRainbowCircle() {
    MaterialTheme {
        RainbowCircle(rememberHazeState(), {})
    }
}