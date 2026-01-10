package pro.jayeshseth.animations.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

/**
 * A composable that displays a container with a row of tabs and the content below them.
 * It applies a "haze" or "glassmorphism" effect to its background, blurring the content behind it.
 * The container has rounded top corners.
 *
 * @param T The type of data in the [tabsList].
 * @param hazeState The state object that controls the haze effect, shared with the background content.
 * @param tabsList A list of data items, one for each tab.
 * @param selectedIndex The index of the currently selected tab.
 * @param tabComponent A composable lambda to render a single tab. It receives the index and the data item for the tab.
 * @param modifier The [Modifier] to be applied to the main column container.
 * @param color The color used for the selected tab's indicator.
 * @param content A composable lambda that defines the content to be displayed below the tab row.
 */
@Composable
fun <T> TabContent(
    hazeState: HazeState,
    tabsList: List<T>,
    selectedIndex: Int,
    tabComponent: @Composable (index: Int, tab: T) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan,
    content: @Composable ColumnScope.() -> Unit,
) {
    val cardStyle = HazeStyle(
        tint = HazeTint(Color.Black.copy(.4f)),
        blurRadius = 50.dp,
        noiseFactor = 0.1f,
    )
    Column(
        modifier
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .hazeEffect(
                state = hazeState,
                style = cardStyle,
            )
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        TabsRow(
            tabsList = tabsList,
            selectedIndex = selectedIndex,
            hazeState = hazeState,
            tabComponent = tabComponent,
            color = color
        )
        content()
    }
}