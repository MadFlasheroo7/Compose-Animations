package pro.jayeshseth.animations.masterCustomization.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoDropDown(
    hazeState: HazeState,
    modifier: Modifier = Modifier,
) {
    val options = listOf("Option 1", "Option 2", "Option 3")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options.first()) }

    val customizationState = LocalCustomizationState.current
    val color by animateColorAsState(
        targetValue = Color(customizationState.accentColorArgb),
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )
    val shadowColor by animateColorAsState(
        targetValue = if (expanded) color else color.copy(.40f),
        animationSpec = tween(500),
        label = "animated shadow color",
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        modifier = modifier
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
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
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                value = selectedOption,
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
                onDismissRequest = { expanded = false },
                shape = RoundedCornerShape(25.dp),
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
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
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


@Preview
@Composable
fun PreviewDropDown() {
    ShaderPreviewContent {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            DemoDropDown(
                hazeState = it
            )
        }
    }
}