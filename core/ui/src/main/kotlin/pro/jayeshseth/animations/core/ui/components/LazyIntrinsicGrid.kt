package pro.jayeshseth.animations.core.ui.components

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A Lazy Grid that forces items in the same row to have equal height.
 * Supports custom column spans for items.
 *
 * @param span A lambda that returns the number of columns an item should occupy. Defaults to 1.
 */
@Composable
fun <T> LazyIntrinsicGrid(
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

    LazyColumn(
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
                            .fillMaxHeight(),
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