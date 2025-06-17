package pro.jayeshseth.animations.ui.screens.shaders

import android.content.ClipData
import android.content.ClipDescription
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pro.jayeshseth.animations.ui.composables.AnimatedTab
import pro.jayeshseth.animations.ui.composables.CodeBlockWithLineNumbers
import pro.jayeshseth.animations.ui.composables.CopyIconButton
import pro.jayeshseth.animations.ui.composables.SliderTemplate
import pro.jayeshseth.animations.ui.composables.TabsRow
import pro.jayeshseth.animations.ui.composables.Toggler
import pro.jayeshseth.animations.ui.shaders.RainbowCircleShader
import pro.jayeshseth.animations.ui.shaders.ShaderIterationLevel
import pro.jayeshseth.animations.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.util.AnimationTabs
import pro.jayeshseth.animations.util.Fields
import pro.jayeshseth.animations.util.RainbowCircleState
import pro.jayeshseth.animations.util.animationTabsList
import pro.jayeshseth.animations.util.lazyNavBarPadding
import pro.jayeshseth.animations.util.sourceCode.RainbowShaderCode
import pro.jayeshseth.commoncomponents.InteractiveButton

@Composable
fun RainbowCircle(modifier: Modifier = Modifier) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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
                    pattern = 2.4f,
                    expand = 1.0f,
                    reduceDots = iterationLevel == ShaderIterationLevel.LOW
                )
            )
        }

        LaunchedEffect(Unit) {
            scope.launch {
                while (true) {
                    time.floatValue += rainbowCircleState.value.speed
                    delay(100)
                }
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
                            rainbowCircleShader.updatePattern({ rainbowCircleState.value.pattern })
                            rainbowCircleShader.updateExpand({ rainbowCircleState.value.expand })
                            rainbowCircleShader.updateBrightness({ rainbowCircleState.value.brightness })
                            rainbowCircleShader.updateLayers({ rainbowCircleState.value.layers })
                            rainbowCircleShader.updateSize({ rainbowCircleState.value.size })
                            rainbowCircleShader.updateResolution(Size(size.width, size.height))
                            rainbowCircleShader.updateColor(
                                { rainbowCircleState.value.red },
                                { rainbowCircleState.value.green },
                                { rainbowCircleState.value.blue },
                                { rainbowCircleState.value.alpha }
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
                                rainbowCircleState = { rainbowCircleState.value },
                                onRainbowCircleStateChange = { rainbowCircleState.value = it }
                            )
                        }

                        AnimationTabs.Code -> {
                            CodePreview(iterationLevel, rainbowCircleState.value)
                        }

                        AnimationTabs.Source -> {
                            LinksButtons()
                        }
                    }
                }
            }
        }
    } else {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Unavailable")
        }
    }
}

@Preview
@Composable
private fun LinksButtons() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            InteractiveButton(
                text = "Blog",
                height = 70.dp,
                onClick = { }
            )
            InteractiveButton(
                text = "Github",
                height = 70.dp,
                onClick = { },
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun CodePreview(
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
                        value = { data.value },
                        step = { data.step },
                        onValueChange = { value -> data.onValueChange(value) },
                        valueRange = data.valueRange,
                        roundToInt = data.roundToInt,
                        titlePadding = PaddingValues(vertical = 10.dp),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                is Fields.ToggleData -> {
                    Toggler(
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
            step = 0.1f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(pattern = it))
            },
            valueRange = 0f..10f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Expand",
            value = sliderData.expand,
            step = 0f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(expand = it))
            },
            valueRange = 0f..10f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Speed",
            value = sliderData.speed,
            step = 0.01f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(speed = it))
            },
            valueRange = -1f..1f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Size",
            value = sliderData.size,
            step = 0.01f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(size = it))
            },
            valueRange = -10f..10f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Red",
            value = sliderData.red,
            step = 0.01f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(red = it))
            },
            valueRange = -10f..10f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Green",
            value = sliderData.green,
            step = 0.01f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(green = it))
            },
            valueRange = -10f..10f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Blue",
            value = sliderData.blue,
            step = 0.01f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(blue = it))
            },
            valueRange = -10f..10f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Alpha",
            value = sliderData.alpha,
            step = 0.01f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(alpha = it))
            },
            valueRange = -1f..1f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Layers",
            value = sliderData.layers,
            step = 1f,
            onValueChange = {
                onSliderDataChange(sliderData.copy(layers = it))
            },
            valueRange = -0f..3000f,
            roundToInt = false
        ),
        Fields.SliderData(
            title = "Brightness",
            value = sliderData.brightness,
            step = 0f,
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
    AnimationsTheme {
        RainbowCircle()
    }
}