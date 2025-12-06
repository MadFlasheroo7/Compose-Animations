package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.theapache64.rebugger.Rebugger
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun <T> TabsRow(
    tabsList: List<T>,
    hazeState: HazeState,
    selectedIndex: Int,
    tabComponent: @Composable (index: Int, tab: T) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan
) {
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        TabButton(
            hazeState,
            tabPositions,
            selectedIndex,
            color
        )
    }
    val cardStyle = HazeStyle(
        tint = HazeTint(Color.Black.copy(.7f)),
        blurRadius = 50.dp,
        noiseFactor = 0.1f,
    )
    TabRow(
        selectedTabIndex = selectedIndex,
        indicator = indicator,
        containerColor = Color.Transparent,
        divider = {},
        modifier = modifier
            .clip(CircleShape)
            .hazeEffect(hazeState, cardStyle)
    ) {
        tabsList.forEachIndexed { index, dateFilter ->
            tabComponent(index, dateFilter)
        }
    }
}

@Composable
fun TabButton(
    hazeState: HazeState,
    tabPositions: List<TabPosition>,
    currentIndex: Int,
    color: Color = Color.Cyan,
) {
    val transition = updateTransition(currentIndex)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val buttonInteracted = isPressed.or(isHovered)

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

    val shadowColor by animateColorAsState(
        targetValue = if (buttonInteracted) color else color.copy(.40f),
        animationSpec = tween(500),
        label = "animated shadow color",
    )

    Box(
        Modifier

            .offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd - indicatorStart)
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(50))
            .hazeEffect(
                state = hazeState,
                HazeStyle(
                    tint = HazeTint(Color.DarkGray.copy(.4f)),
                    blurRadius = 50.dp,
                    noiseFactor = .1f,
                )
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {}
            )
            .innerShadow(RoundedCornerShape(50)) {
                this.color = shadowColor
                this.radius = 10f
                this.spread = 10f
            }
//            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(50))
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
    color: Color = Color.Cyan,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
    content: @Composable () -> Unit
) {
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
        CompositionLocalProvider(LocalContentColor provides color) {
            content()
        }
    }
}


@Preview
@Composable
fun PreviewTab() {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Tab 1", "Tab 2", "Tab 3")
    ShaderPreviewContent {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            TabsRow(
                tabsList = titles,
                hazeState = it,
                selectedIndex = state,
                tabComponent = { index, tab ->

                    AnimatedTab(
                        isSelected = state == index,
                        onClick = { state = index },
                    ) {
                        Text(tab, color = Color.White)
                    }
                }
            )
        }
    }
}
