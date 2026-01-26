package pro.jayeshseth.animations.core.ui.layouts

import androidx.compose.animation.animateBounds
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.ui.components.PrimaryInteractiveButton
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.DESKTOP
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.MOBILE_LANDSCAPE
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.MOBILE_PORTRAIT
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.TABLET_LANDSCAPE
import pro.jayeshseth.animations.core.ui.utils.DeviceConfiguration.TABLET_PORTRAIT
import pro.jayeshseth.animations.core.ui.utils.currentDeviceConfiguration

/**
 * A Lazy Grid that forces items in the same row to have equal height.
 * Supports custom column spans for items.
 *
 * @param span A lambda that returns the number of columns an item should occupy. Defaults to 1.
 */
@Composable
fun <T> LazyIntrinsicGrid(
    state: LazyListState = rememberLazyListState(),
    items: List<T>,
    columns: Int,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(20.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(20.dp),
    span: (item: T) -> Int = { 1 },
    content: @Composable (index: Int, item: T) -> Unit
) {
    val gridRows = remember(items, columns) {
        buildGridRows(items, columns, span)
    }
    LookaheadScope {
        LazyColumn(
            state = state,
            modifier = modifier,
            contentPadding = contentPadding,
            verticalArrangement = verticalArrangement
        ) {
            itemsIndexed(gridRows) { index, rowItems ->
                val totalSpanUsed = rowItems.sumOf { it.span }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = horizontalArrangement
                ) {
                    rowItems.forEach { wrapper ->
                        Box(
                            modifier = Modifier
                                .weight(wrapper.span.toFloat())
                                .fillMaxHeight()
                                .animateBounds(this@LookaheadScope),
                            propagateMinConstraints = true
                        ) {
                            content(index, wrapper.item)
                        }
                    }

                    if (totalSpanUsed < columns) {
                        val missingSpans = columns - totalSpanUsed
                        Spacer(modifier = Modifier.weight(missingSpans.toFloat()))
                    }
                }
            }
        }
    }

}


/**
 * Logic to group items into rows based on their requested span and available columns.
 */
private fun <T> buildGridRows(
    items: List<T>,
    columns: Int,
    spanProvider: (T) -> Int
): List<List<GridItem<T>>> {
    val rows = mutableListOf<List<GridItem<T>>>()
    var currentRow = mutableListOf<GridItem<T>>()
    var currentSpanSum = 0

    items.forEach { item ->
        val itemSpan = spanProvider(item).coerceIn(1, columns)

        if (currentSpanSum + itemSpan > columns) {
            rows.add(currentRow)
            currentRow = mutableListOf()
            currentSpanSum = 0
        }

        currentRow.add(GridItem(item, itemSpan))
        currentSpanSum += itemSpan
    }

    if (currentRow.isNotEmpty()) rows.add(currentRow)

    return rows
}

/**
 * Helper class to hold the item and its assigned span for the grid.
 */
private data class GridItem<T>(
    val item: T,
    val span: Int
)

@Preview
@Composable
fun PreviewLazyGrid() {
    ShaderPreviewContent {
        val deviceConfiguration = currentDeviceConfiguration()

        val columns by rememberUpdatedState(
            newValue = when (deviceConfiguration) {
                MOBILE_PORTRAIT -> 1
                MOBILE_LANDSCAPE -> 2
                TABLET_PORTRAIT -> 2
                TABLET_LANDSCAPE -> 4
                DESKTOP -> 4
            }
        )

        LazyIntrinsicGrid(
            items = (1..100).toList(),
            columns = columns
        ) { index, item ->

            PrimaryInteractiveButton(
                hazeState = it,
                color = Color.White,
                text = "Shape And Morphing",
                onClick = {},
                modifier = Modifier,
                scale = 1f,
                blur = 0f
            )

        }
    }
}