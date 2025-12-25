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