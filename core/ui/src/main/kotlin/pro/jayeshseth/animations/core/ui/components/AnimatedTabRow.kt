package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
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
import kotlin.math.ln
import kotlin.math.sqrt

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
        TabIndicator(
            hazeState,
            tabPositions,
            selectedIndex,
            color
        )
    }
    val cardStyle = HazeStyle(
        tint = HazeTint(Color.Black.copy(.6f)),
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
        tabsList.forEachIndexed { index, tab ->
            tabComponent(index, tab)
        }
    }
}

@Composable
private fun TabIndicator(
    hazeState: HazeState,
    tabPositions: List<TabPosition>,
    currentIndex: Int,
    color: Color = Color.Cyan,
) {
    val stiffness = 1000f
    val dampingRatio = 1f
    val threshold = 0.001f
    val s2 = 50f

    val settlingTime = -ln(threshold) / (dampingRatio * sqrt(stiffness))
    val durationMs = (settlingTime * 1000).toInt()

    val settlingTime2 = -ln(threshold) / (dampingRatio * sqrt(s2))
    val durationMs2 = (settlingTime2 * 1000).toInt()

    var previousIndex by remember { mutableStateOf(currentIndex) }
    val animatingForward = currentIndex > previousIndex

    LaunchedEffect(currentIndex) {
        previousIndex = currentIndex
    }

    val indicatorStart by animateDpAsState(
        targetValue = tabPositions[currentIndex].left,
        animationSpec = if (animatingForward) {
            tween(durationMillis = durationMs2, easing = FastOutSlowInEasing)
        } else {
            tween(durationMillis = durationMs, easing = FastOutSlowInEasing)
        },
        label = "animated start point"
    )

    val indicatorEnd by animateDpAsState(
        targetValue = tabPositions[currentIndex].right,
        animationSpec = if (animatingForward) {
            tween(durationMillis = durationMs, easing = FastOutSlowInEasing)
        } else {
            tween(durationMillis = durationMs2, easing = FastOutSlowInEasing)
        },
        label = "animating end point"
    )

    val isAnimating = indicatorStart != tabPositions[currentIndex].left ||
            indicatorEnd != tabPositions[currentIndex].right

    val shadowColor by animateColorAsState(
        targetValue = if (isAnimating) color else color.copy(.40f),
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
            .innerShadow(RoundedCornerShape(50)) {
                this.color = shadowColor
                this.radius = 10f
                this.spread = 10f
            }
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
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
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
                interactionSource = interactionSource
            )
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
