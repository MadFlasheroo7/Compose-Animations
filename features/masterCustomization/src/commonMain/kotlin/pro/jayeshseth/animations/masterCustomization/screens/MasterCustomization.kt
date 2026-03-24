package pro.jayeshseth.animations.masterCustomization.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateBounds
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.AnimationContent
import pro.jayeshseth.animations.core.ui.components.AnimatedTab
import pro.jayeshseth.animations.core.ui.components.AnimationCard
import pro.jayeshseth.animations.core.ui.components.HeadingText
import pro.jayeshseth.animations.core.ui.components.LocalSharedTransitionScope
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContentWithSharedTransitionScope
import pro.jayeshseth.animations.core.ui.components.TabContent
import pro.jayeshseth.animations.core.ui.utils.currentDeviceConfiguration
import pro.jayeshseth.animations.masterCustomization.components.BackgroundCustomizationContent
import pro.jayeshseth.animations.masterCustomization.components.DemoDropDown
import pro.jayeshseth.animations.masterCustomization.components.DemoInteractiveButtons
import pro.jayeshseth.animations.masterCustomization.components.DemoSlider
import pro.jayeshseth.animations.masterCustomization.components.DemoSwitch
import pro.jayeshseth.animations.masterCustomization.components.ThemeCustomizationContent
import pro.jayeshseth.animations.masterCustomization.screens.StandardPages.Background
import pro.jayeshseth.animations.masterCustomization.screens.StandardPages.Buttons
import pro.jayeshseth.animations.masterCustomization.screens.StandardPages.DropDownMenu
import pro.jayeshseth.animations.masterCustomization.screens.StandardPages.Slider
import pro.jayeshseth.animations.masterCustomization.screens.StandardPages.Switch
import pro.jayeshseth.animations.masterCustomization.screens.StandardPages.entries

@Composable
fun MasterCustomization(
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {

//    val customizationState = LocalCustomizationState.current
//    val accentColor = Color(customizationState.buttonAccentColorArgb)

    val pagerState = rememberPagerState { MasterCustomizationTabs.entries.size }
    val pagerState2 = rememberPagerState { entries.size }
    val scope = rememberCoroutineScope { Dispatchers.Default }

    var forward by remember { mutableStateOf(false) }

    val deviceConfig = currentDeviceConfiguration()
    val isWideScreen = deviceConfig.isWideScreen

    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this
        ) {
            AnimatedContent(
                targetState = isWideScreen,
                transitionSpec = {
                    fadeIn(tween(400)) togetherWith fadeOut(tween(400)) using
                            SizeTransform { initialSize, targetSize ->
                                tween(400, easing = FastOutSlowInEasing)
                            }
                },
                modifier = modifier.fillMaxSize()
            ) { isWide ->
                if (isWide) {
                    WideScreenMasterCustomization(
                        hazeState = hazeState,
                        pagerState = pagerState,
//                        color = accentColor,
                        onClick = {
                            scope.launch {
                                val nextPage = if (forward) {
                                    if (pagerState2.currentPage < entries.size - 1) {
                                        pagerState2.currentPage + 1
                                    } else {
                                        forward = false // Switch direction
                                        pagerState2.currentPage - 1
                                    }
                                } else {
                                    if (pagerState2.currentPage > 0) {
                                        pagerState2.currentPage - 1
                                    } else {
                                        forward = true // Switch direction
                                        pagerState2.currentPage + 1
                                    }
                                }
                                pagerState2.animateScrollToPage(nextPage)
                            }

                        }
//                        pagerState2 = pagerState2
                    )
                } else {
                    StandardMasterCustomization(
                        hazeState = hazeState,
                        pagerState = pagerState,
//                        color = accentColor,
                        onClick = {
                            scope.launch {
                                val nextPage = if (forward) {
                                    if (pagerState2.currentPage < entries.size - 1) {
                                        pagerState2.currentPage + 1
                                    } else {
                                        forward = false // Switch direction
                                        pagerState2.currentPage - 1
                                    }
                                } else {
                                    if (pagerState2.currentPage > 0) {
                                        pagerState2.currentPage - 1
                                    } else {
                                        forward = true // Switch direction
                                        pagerState2.currentPage + 1
                                    }
                                }
                                pagerState2.animateScrollToPage(nextPage)
                            }

                        },
                        pagerState2 = pagerState2,
                    )
                }
            }
        }
    }
}

enum class StandardPages {
    Background, Buttons, Slider, Switch, DropDownMenu
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedContentScope.StandardMasterCustomization(
    hazeState: HazeState,
    pagerState: PagerState,
    pagerState2: PagerState,
//    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedTransitionScope found")

    with(sharedTransitionScope) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .animateContentSize(tween(300))
        ) {
            // Top Section
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                ),
                title = {
                    HeadingText("Master Customization")
                }
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .sharedBounds(
                        rememberSharedContentState(key = "customization-panel"),
                        this@StandardMasterCustomization,
                        boundsTransform = { _, _ -> tween(300) }
                    )
            ) {
                CustomizationPager(
                    hazeState,
                    pagerState2,
                    sharedTransitionScope,
                    this@StandardMasterCustomization,
//                    color = color
                )
            }

            // Bottom Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .sharedBounds(
                        rememberSharedContentState(key = "tabs-panel"),
                        this@StandardMasterCustomization,
                        boundsTransform = { _, _ -> tween(300) }
                    )
            ) {
                TabsSection(hazeState, pagerState, onClick)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedVisibilityScope.WideScreenMasterCustomization(
    hazeState: HazeState,
    pagerState: PagerState,
//    color: Color,
    onClick: () -> Unit
//    pagerState2: PagerState
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedTransitionScope found")

    with(sharedTransitionScope) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize(tween(300))
                .animateBounds(sharedTransitionScope)
        ) {
            // Left Panel
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .sharedBounds(
                        rememberSharedContentState(key = "customization-panel"),
                        this@WideScreenMasterCustomization,
                        boundsTransform = { _, _ -> tween(300) }
                    )
            ) {
                Column {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Transparent,
                        ),
                        title = {
                            HeadingText("Master Customization")
                        }
                    )
                    AnimationCard(
                        index = 0,
                        hazeState = hazeState,
                        onClickLink = {},
                        modifier = Modifier
                            .padding(32.dp),
                        animationContent = AnimationContent(
                            title = "Background",
                            content = {
//                                BackgroundCustomizationContent()
                            }
                        )
                    )
                    DemoInteractiveButtons(
                        hazeState,
                        modifier = Modifier
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.InteractiveButtons),
//                                this@WideScreenMasterCustomization,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
                    )
                    DemoSlider(
                        hazeState, modifier = Modifier,
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.Slider),
//                                this@WideScreenMasterCustomization,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
                    )
                    DemoSwitch(
                        hazeState = hazeState, toggled = true, onToggleChanged = {},
                        modifier = Modifier,
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.Switch),
//                                this@WideScreenMasterCustomization,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
//                        color = color
                    )

                    DemoDropDown(
                        value = "Option 1",
                        hazeState = hazeState,
                        expanded = false,
                        onExpandedChange = {},
                        onDismissRequest = {},
//                        color = color,
                        content = {},
                        modifier = Modifier
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.DropDown),
//                                this@WideScreenMasterCustomization,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )

                    )
                }
//                CustomizationPager(pagerState2)
            }
//
//            VerticalDivider(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .width(1.dp)
//                    .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
//            )

            // Right Panel
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .sharedBounds(
                        rememberSharedContentState(key = "tabs-panel"),
                        this@WideScreenMasterCustomization,
                        boundsTransform = { _, _ -> tween(300) }
                    )
            ) {
                TabsSection(hazeState, pagerState, onClick)
            }
        }
    }
}

@Preview()
@Composable
private fun CustomizationPager(
    hazeState: HazeState,
    pagerState: PagerState,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
//            .padding(16.dp)
                .aspectRatio(1f)
//            .fillMaxSize()
//            .background(Color.Black)
        ) { page ->
            when (entries[page]) {
                Background -> {
                    Box(Modifier.size(100.dp))
                }

                Buttons -> {
                    DemoInteractiveButtons(
                        hazeState,
                        modifier = Modifier
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.InteractiveButtons),
//                                animatedContentScope,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
                    )

                }

                Slider -> {
                    DemoSlider(
                        hazeState,
                        modifier = Modifier,
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.Slider),
//                                animatedContentScope,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
//                        color = color
                    )
                }

                Switch -> {
                    DemoSwitch(
                        hazeState = hazeState, toggled = true, onToggleChanged = {},
                        modifier = Modifier,
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.Switch),
//                                animatedContentScope,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
//                        color = color
                    )
                }

                DropDownMenu -> {
                    DemoDropDown(
                        value = "Option 1",
                        hazeState = hazeState,
                        expanded = true,
                        onExpandedChange = {},
                        onDismissRequest = {},
//                        color = color,
                        content = {},
                        modifier = Modifier
//                            .sharedBounds(
//                                rememberSharedContentState(key = MasterCustomizationSharedKeys.DropDown),
//                                animatedContentScope,
//                                boundsTransform = { _, _ -> tween(300) }
//                            )
                    )

                }
            }
        }
    }
}

object MasterCustomizationSharedKeys {
    val InteractiveButtons = "interactive_buttons"
    val Slider = "interactive_buttons"
    val Switch = "interactive_buttons"
    val DropDown = "interactive_buttons"
}

// TODO move to a separate component


@Composable
private fun TabsSection(
    hazeState: HazeState,
    pagerState: PagerState,
    onClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    TabContent(
        hazeState = hazeState,
        modifier = Modifier.fillMaxSize(),
        tabsList = MasterCustomizationTabs.entries,
        selectedIndex = pagerState.currentPage,
        tabComponent = { index, tab ->
            AnimatedTab(onClick = {
                scope.launch { pagerState.animateScrollToPage(index) }
                onClick()
            }) {
                Text(tab.name)
            }
        }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (MasterCustomizationTabs.entries[page]) {
                MasterCustomizationTabs.Theme -> ThemeCustomizationContent(hazeState)
                MasterCustomizationTabs.Background -> BackgroundCustomizationContent()
            }
        }
    }
}

// CompositionLocal for SharedTransitionScope


enum class MasterCustomizationTabs {
    Theme, Background
}

@Preview
@Composable
private fun PreviewMasterCustomization() {
    ShaderPreviewContent {
        MasterCustomization(it)
    }
}

@Preview
@Composable
private fun PreviewStandardMasterCustomization() {
    ShaderPreviewContentWithSharedTransitionScope { sharedTransitionScope, hazeState ->
        AnimatedContent(true) {
            StandardMasterCustomization(
                hazeState = hazeState,
                pagerState = rememberPagerState { 6 },
                pagerState2 = rememberPagerState { entries.size },
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewWideScreenMasterCustomization() {
    ShaderPreviewContentWithSharedTransitionScope { sharedTransitionScope, hazeState ->
        AnimatedContent(true) {
            WideScreenMasterCustomization(
                hazeState = hazeState,
                pagerState = rememberPagerState { 6 },
                onClick = {},
//                pagerState2 = rememberPagerState { StandardPages.entries.size },
            )
        }
    }
}