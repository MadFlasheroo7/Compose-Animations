@file:OptIn(ExperimentalFlexBoxApi::class)

package pro.jayeshseth.animations

import androidx.compose.animation.animateBounds
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalFlexBoxApi
import androidx.compose.foundation.layout.FlexAlignContent
import androidx.compose.foundation.layout.FlexAlignItems
import androidx.compose.foundation.layout.FlexBox
import androidx.compose.foundation.layout.FlexDirection
import androidx.compose.foundation.layout.FlexJustifyContent
import androidx.compose.foundation.layout.FlexWrap
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.rememberHazeState
import pro.jayeshseth.animations.core.ui.components.SliderTemplate


private val flexJustifyContents = listOf(
    FlexJustifyContent.SpaceAround,
    FlexJustifyContent.Center,
    FlexJustifyContent.End,
    FlexJustifyContent.SpaceBetween,
    FlexJustifyContent.Start,
    FlexJustifyContent.SpaceEvenly,
)

private val flexWrapList = listOf(
    FlexWrap.NoWrap,
    FlexWrap.Wrap,
    FlexWrap.WrapReverse,
)

private val flexDirection = listOf(
    FlexDirection.Row,
    FlexDirection.RowReverse,
    FlexDirection.Column,
    FlexDirection.ColumnReverse,
)

private val flexContentAlignment = listOf(
    FlexAlignContent.Center,
    FlexAlignContent.End,
    FlexAlignContent.SpaceAround,
    FlexAlignContent.SpaceBetween,
    FlexAlignContent.Start,
    FlexAlignContent.Stretch,
)

private val flexAlignItems = listOf(
    FlexAlignItems.Center,
    FlexAlignItems.End,
    FlexAlignItems.Start,
    FlexAlignItems.Stretch,
    FlexAlignItems.Baseline,
)

@Preview
@OptIn(ExperimentalFlexBoxApi::class)
@Composable
fun FlexBoxSample(modifier: Modifier = Modifier) {
    var justify by remember { mutableFloatStateOf(1f) }
    var flexWrap by remember { mutableFloatStateOf(1f) }
    var direction by remember { mutableFloatStateOf(0f) }
    var contentAlignment by remember { mutableFloatStateOf(0f) }
    var alignItems by remember { mutableFloatStateOf(0f) }
    var rowGap by remember { mutableFloatStateOf(0f) }
    var columnGap by remember { mutableFloatStateOf(0f) }
    var itemCount by remember { mutableIntStateOf(50) }


    LookaheadScope {
        Row(
            horizontalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .animateContentSize()
                .navigationBarsPadding(),
        ) {
            FlexBox(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                config = {
                    this.wrap = flexWrapList.get(flexWrap.toInt())
                    this.direction = flexDirection[direction.toInt()]
                    this.alignContent = flexContentAlignment[contentAlignment.toInt()]
                    this.alignItems = flexAlignItems[alignItems.toInt()]
                    this.justifyContent = flexJustifyContents.get(justify.toInt())
                    this.columnGap = columnGap.dp
                    this.rowGap = rowGap.dp
                },
            ) {
                repeat(itemCount) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .animateBounds(
                                this@LookaheadScope,
                                animateMotionFrameOfReference = true
                            )
                            .background(Color.Cyan)
                            .border(1.dp, Color.Gray),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Fixed")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(25.dp),
            ) {
                SliderTemplate(
                    title = "Item Count: $itemCount",
                    value = { itemCount.toFloat() },
                    onValueChange = { itemCount = it.toInt() },
                    valueRange = 10f..100f,
                    step = { 0 },
                    onIncrement = {
                        if (itemCount < 100) {
                            itemCount += 10
                        }
                    },
                    onDecrement = {
                        if (itemCount > 10) {
                            itemCount -= 10
                        }
                    },
                    hazeState = rememberHazeState()
                )

                SliderTemplate(
                    title = "Justify: ${flexJustifyContents[justify.toInt()]}",
                    value = { justify },
                    onValueChange = { justify = it },
                    valueRange = 0f..flexJustifyContents.size.toFloat() - 1f,
                    step = { flexJustifyContents.size },
                    onIncrement = {
                        if (justify < flexJustifyContents.size.toFloat() - 1) {
                            justify += 1
                        }
                    },
                    onDecrement = {
                        if (justify > 0) {
                            justify -= 1
                        }
                    },
                    hazeState = rememberHazeState()
                )

                SliderTemplate(
                    title = "Wrap: ${flexWrapList[flexWrap.toInt()]}",
                    value = { flexWrap },
                    onValueChange = { flexWrap = it },
                    valueRange = 0f..flexWrapList.size.toFloat() - 1f,
                    step = { flexWrapList.size },
                    onIncrement = {
                        if (flexWrap < flexWrapList.size.toFloat() - 1) {
                            flexWrap += 1
                        }
                    },
                    onDecrement = {
                        if (flexWrap > 0) {
                            flexWrap -= 1
                        }
                    },
                    hazeState = rememberHazeState()
                )

                SliderTemplate(
                    title = "Direction: ${flexDirection[direction.toInt()]}",
                    value = { direction },
                    onValueChange = { direction = it },
                    valueRange = 0f..flexDirection.size.toFloat() - 1f,
                    step = { flexDirection.size },
                    onIncrement = {
                        if (direction < flexDirection.size.toFloat() - 1) {
                            direction += 1
                        }
                    },
                    onDecrement = {
                        if (direction > 0) {
                            direction -= 1
                        }
                    },
                    hazeState = rememberHazeState()
                )

                SliderTemplate(
                    title = "Alignment: ${flexContentAlignment[contentAlignment.toInt()]}",
                    value = { contentAlignment },
                    onValueChange = { contentAlignment = it },
                    valueRange = 0f..flexContentAlignment.size.toFloat() - 1f,
                    step = { flexContentAlignment.size },
                    onIncrement = {
                        if (contentAlignment < flexContentAlignment.size.toFloat() - 1) {
                            contentAlignment += 1
                        }
                    },
                    onDecrement = {
                        if (contentAlignment > 0) {
                            contentAlignment -= 1
                        }
                    },
                    hazeState = rememberHazeState()
                )

                SliderTemplate(
                    title = "AlignItems: ${flexAlignItems[alignItems.toInt()]}",
                    value = { alignItems },
                    onValueChange = { alignItems = it },
                    valueRange = 0f..flexAlignItems.size.toFloat() - 1f,
                    step = { flexAlignItems.size },
                    onIncrement = {
                        if (alignItems < flexAlignItems.size.toFloat() - 1) {
                            alignItems += 1
                        }
                    },
                    onDecrement = {
                        if (alignItems > 0) {
                            alignItems -= 1
                        }
                    },
                    hazeState = rememberHazeState()
                )

                SliderTemplate(
                    title = "RowGap: ${rowGap.dp}",
                    value = { rowGap },
                    onValueChange = { rowGap = it },
                    valueRange = 0f..50f,
                    step = { 0 },
                    hazeState = rememberHazeState(),
                    onIncrement = {
                        if (rowGap < 50f) {
                            rowGap += 1f
                        }
                    },
                    onDecrement = {
                        if (rowGap > 0f) {
                            rowGap -= 1f
                        }
                    }
                )

                SliderTemplate(
                    title = "ColumnGap: ${columnGap.dp}",
                    value = { columnGap },
                    onValueChange = { columnGap = it },
                    valueRange = 0f..50f,
                    step = { 0 },
                    hazeState = rememberHazeState(),
                    onIncrement = {
                        if (columnGap < 50f) {
                            columnGap += 1f
                        }
                    },
                    onDecrement = {
                        if (columnGap > 0f) {
                            columnGap -= 1f
                        }
                    },
                )
            }
        }
    }
}