package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationState
import kotlin.math.ln
import kotlin.math.sqrt

/**
 * A composable function that displays a row of tabs with a glassmorphism effect
 * and a custom animated indicator. The background of the tab row uses the `hazeEffect`
 * to create a blurred, translucent look. The selected tab is highlighted with an
 * indicator that animates its position and size as the selection changes.
 *
 * @param T The type of data in the `tabsList`.
 * @param tabsList A list of items to be displayed as tabs.
 * @param hazeState The [HazeState] used to control the background blur effect.
 * @param selectedIndex The index of the currently selected tab.
 * @param tabComponent A composable lambda that defines the UI for each individual tab.
 *                     It receives the `index` and the `tab` item.
 * @param modifier The [Modifier] to be applied to the `TabRow`.
 * @param color The color used for the tab indicator's shadow effect.
 */
@Composable
fun <T> TabsRow(
    tabsList: List<T>,
    hazeState: HazeState,
    selectedIndex: Int,
    tabComponent: @Composable (index: Int, tab: T) -> Unit,
    modifier: Modifier = Modifier,
) {
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        TabIndicator(
            hazeState,
            tabPositions,
            selectedIndex
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

/**
 * A composable function that renders the animated indicator for the [TabsRow].
 *
 * This indicator has a unique animation behavior: when moving to the next tab (forward),
 * the start of the indicator moves slowly while the end moves quickly. When moving to a
 * previous tab (backward), the start moves quickly while the end moves slowly. This creates
 * a "stretching" and "compressing" effect.
 *
 * It uses the `hazeEffect` to create a glassmorphism look, consistent with the parent `TabsRow`,
 * and an `innerShadow` for depth. The shadow color also animates to be more intense
 * during the movement animation.
 *
 * @param hazeState The [HazeState] used to control the background blur effect of the indicator.
 * @param tabPositions A list of [TabPosition]s provided by the `TabRow`'s indicator lambda,
 *                     representing the positions of each tab.
 * @param currentIndex The index of the currently selected tab.
 */
@Composable
private fun TabIndicator(
    hazeState: HazeState,
    tabPositions: List<TabPosition>,
    currentIndex: Int,
) {
    val customizationState = LocalCustomizationState.current
    val color by animateColorAsState(
        targetValue = Color(customizationState.accentColorArgb),
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

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

//    Rebugger(
//        composableName = "tab button",
//        trackMap = mapOf(
//            "tabPositions" to tabPositions,
//            "currentIndex" to currentIndex,
//            "indicatorStart" to indicatorStart,
//            "indicatorEnd" to indicatorEnd,
//        )
//    )
}


/**
 * A composable function that represents a single tab item within the [TabsRow].
 *
 * This tab is designed to be clickable and has a rounded shape. It uses [CompositionLocalProvider]
 * to set the local content color, which can be used by child composables like `Text`.
 * The `zIndex` is set to ensure it appears above the animated indicator background.
 *
 * @param onClick A lambda function to be invoked when the tab is clicked.
 * @param modifier The [Modifier] to be applied to the tab's container.
 * @param interactionSource The [MutableInteractionSource] for this clickable component.
 * @param contentPadding The padding applied inside the tab, around the `content`.
 * @param content The composable content to be displayed inside the tab, such as a [Text] label.
 */
@Composable
fun AnimatedTab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
    content: @Composable () -> Unit
) {
    val customizationState = LocalCustomizationState.current
    val color by animateColorAsState(
        targetValue = Color(customizationState.accentColorArgb),
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
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
                        onClick = { state = index },
                    ) {
                        Text(tab, color = Color.White)
                    }
                }
            )
        }
    }
}
