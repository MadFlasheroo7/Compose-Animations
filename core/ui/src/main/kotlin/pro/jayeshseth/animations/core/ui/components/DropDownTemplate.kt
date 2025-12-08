package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownTemplate(
    hazeState: HazeState,
    value: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    title: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
    color: Color = Color.Cyan,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    var isMenuAbove by remember { mutableStateOf(false) }
    var anchorCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
    val alpha by animateFloatAsState(
        targetValue = if (expanded) 1f else .4f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    )

    val spread by animateFloatAsState(
        targetValue = if (expanded) 20f else 10f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    )

    val radius by animateFloatAsState(
        targetValue = if (expanded) 20f else 10f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow,
        ),
    )
    val shadowColor by animateColorAsState(
        targetValue = if (expanded) color else color.copy(.40f),
        animationSpec = tween(500),
        label = "animated shadow color",
    )
    ControllerTemplate(
        modifier = modifier,
        title = title,
        content = {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                modifier = Modifier
                    .clip(CircleShape)
                    .hazeEffect(
                        state = hazeState,
                        HazeStyle(
                            tint = HazeTint(Color.Black.copy(.6f)),
                            blurRadius = 50.dp,
                            noiseFactor = .2f,
                        )
                    )
                    .innerShadow(CircleShape) {
                        this.spread = spread
                        this.radius = radius
                        this.color = shadowColor
                    }
                    .onGloballyPositioned { coordinates ->
                        anchorCoordinates = coordinates
                    }
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    value = value,
                    onValueChange = {},
                    readOnly = true,
                    shape = CircleShape,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = onDismissRequest,
                    shape = RoundedCornerShape(25.dp),
                    content = content,
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp,
                    shadowElevation = 0.dp,
                    modifier = Modifier
                        .hazeEffect(
                            state = hazeState,
                            HazeStyle(
                                tint = HazeTint(Color.Black.copy(.6f)),
                                blurRadius = 50.dp,
                                noiseFactor = .2f,
                            )
                        )
                        .innerShadow(RoundedCornerShape(25.dp)) {
                            this.spread = spread
                            this.radius = radius
                            this.color = shadowColor
                        }
                )
            }
        }
    )
}

@Composable
fun dropDownShape(
    isExpanded: Boolean,
    isAtTop: Boolean,
    cornerRadius: Dp = 30.dp
): Shape {
    val animatedShape by animateDpAsState(
        targetValue = if (isExpanded) 0.dp else cornerRadius,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = "animated dropdown shape"
    )

    return if (isAtTop) {
        RoundedCornerShape(
            topStart = animatedShape,
            topEnd = animatedShape,
            bottomStart = cornerRadius,
            bottomEnd = cornerRadius
        )
    } else {
        RoundedCornerShape(
            topStart = cornerRadius,
            topEnd = cornerRadius,
            bottomStart = animatedShape,
            bottomEnd = animatedShape
        )
    }
}

@Preview
@Composable
fun DropDownTemplatePreview() {
    var expanded by remember { mutableStateOf(true) }
    val options = listOf("Option 1", "Option 2", "Option 3")
    var selectedOption by remember { mutableStateOf(options.first()) }
    ShaderPreviewContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .hazeEffect(
                    state = it,
                    HazeStyle(
                        tint = HazeTint(Color.Black.copy(.7f)),
                        blurRadius = 50.dp,
                        noiseFactor = .2f,
                    )
                )
        ) {
            DropDownTemplate(
                value = selectedOption,
                hazeState = it,
                expanded = expanded,
                onExpandedChange = { expanded = it },
                title = { Text(text = "Sample Dropdown") },
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        modifier = Modifier,
                        onClick = {
                            selectedOption = option
                            expanded = false
                        }
                    )
                }
            }
        }
    }

}