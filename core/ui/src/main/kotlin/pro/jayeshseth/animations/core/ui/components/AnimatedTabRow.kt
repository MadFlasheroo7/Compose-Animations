package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.theapache64.rebugger.Rebugger

@Composable
fun <T> TabsRow(
    tabsList: List<T>,
    selectedIndex: Int,
    tabComponent: @Composable (index: Int, tab: T) -> Unit,
    modifier: Modifier = Modifier
) {
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        TabButton(
            tabPositions,
            selectedIndex
        )
    }
    TabRow(
        selectedTabIndex = selectedIndex,
        indicator = indicator,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        divider = {},
        modifier = modifier.clip(CircleShape)
    ) {
        tabsList.forEachIndexed { index, dateFilter ->
            tabComponent(index, dateFilter)
        }
    }
}

@Composable
fun TabButton(
    tabPositions: List<TabPosition>,
    currentIndex: Int,
) {
    val transition = updateTransition(currentIndex)

    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 1000f)
            }
        }, label = "animated start point"
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 1000f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        }, label = "animating end point"
    ) {
        tabPositions[it].right
    }

    Box(
        Modifier
            .offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd - indicatorStart)
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(50))
            .zIndex(1f)
    )

    Rebugger(
        composableName = "tab button",
        trackMap = mapOf(
            "tabPositions" to tabPositions,
            "currentIndex" to currentIndex,
            "indicatorStart" to indicatorStart,
            "indicatorEnd" to indicatorEnd,
        )
    )
}


@Composable
fun AnimatedTab(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
    content: @Composable () -> Unit
) {
    val transition = updateTransition(isSelected)
    val textColor by transition.animateColor {
        if (it) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSecondaryContainer
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(50))
            .zIndex(2f)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() })
            .padding(contentPadding)
    ) {
        CompositionLocalProvider(LocalContentColor provides textColor) {
            content()
        }
    }
}


@PreviewLightDark
@PreviewDynamicColors
@Composable
fun PreviewTab() {

    var state by remember { mutableStateOf(0) }
    val titles = listOf("Tab 1", "Tab 2", "Tab 3")
    MaterialTheme {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            TabsRow(
                tabsList = titles,
                selectedIndex = state,
                tabComponent = { index, tab ->

                    AnimatedTab(
                        isSelected = state == index,
                        onClick = { state = index },
                    ) {
                        Text(tab)
                    }
                }
            )
        }
    }
}
