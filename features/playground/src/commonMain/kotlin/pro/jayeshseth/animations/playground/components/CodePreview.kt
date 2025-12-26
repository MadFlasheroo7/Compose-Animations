package pro.jayeshseth.animations.playground.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.model.lazyNavBarPadding
import pro.jayeshseth.animations.core.ui.components.AnimatedTab
import pro.jayeshseth.animations.core.ui.components.CodeBlockWithLineNumbers
import pro.jayeshseth.animations.core.ui.components.CopyIconButton
import pro.jayeshseth.animations.core.ui.components.TabsRow
import pro.jayeshseth.animations.playground.model.TweenAndSpringCode
import pro.jayeshseth.animations.playground.model.TweenAndSpringSpecState
import pro.jayeshseth.animations.playground.model.TweenNSpringSpec

@Composable
fun CodePreview(
    hazeState: HazeState,
    state: TweenAndSpringSpecState,
    modifier: Modifier = Modifier
) {
//    val tabsList = remember { mutableStateListOf(
//
//        "Tween") }
    val selectedTab = remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    // TODO
    val clipboardManager = LocalClipboard.current

    var code by remember {
        mutableStateOf(
            if (state.selectedSpec == TweenNSpringSpec.Tween) {
                TweenAndSpringCode(
                    state
                ).tweenCode()
            } else {
                TweenAndSpringCode(
                    state
                ).springCode()
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
//                            clipboardManager.setClipEntry(
//                                clipEntry = ClipEntry(
//                                    clipData = ClipData(
//                                        ClipDescription(
//                                            "animation spec code",
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
                    tabsList = listOf(""),
                    selectedIndex = 0,
                    modifier = Modifier.padding(start = 16.dp),
                    hazeState = hazeState,
                    tabComponent = { index, tabTitle ->
                        AnimatedTab(
                            isSelected = selectedTab.intValue == index,
                            onClick = {},
                        ) {
                            Text(
                                state.selectedSpec.name,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
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
}
