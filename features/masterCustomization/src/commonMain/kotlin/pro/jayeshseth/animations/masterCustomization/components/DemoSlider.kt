package pro.jayeshseth.animations.masterCustomization.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.chrisbanes.haze.HazeState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.ui.components.HazedIconButton
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.core.ui.components.StretchySlider
import pro.jayeshseth.animations.core.ui.icons.AnimIcons

@Composable
fun DemoSlider(
    hazeState: HazeState,
    modifier: Modifier = Modifier,
//    color: Color = Color.Cyan
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(16.dp)
    ) {
        HazedIconButton(onClick = {}, hazeState = hazeState) {
            Icon(
                imageVector = AnimIcons.add,
                contentDescription = "increment",
                tint = Color.White,
            )
        }
        StretchySlider(
            value = { 1f },
            onValueChange = {},
            valueRange = 0f..2f,
            hazeState = hazeState,
//            interactionSource = interactionSource,
//            steps = step,
            modifier = Modifier
                .zIndex(1f)
                .weight(1f)
        )
        HazedIconButton(onClick = {}, hazeState = hazeState) {
            Icon(
                painter = painterResource(AnimIcons.minus),
                contentDescription = "decrement",
                tint = Color.White,
            )
        }
    }
}

@Preview
@Composable
fun PreviewSlider() {
    ShaderPreviewContent {
        DemoSlider(
            hazeState = it
        )
    }
}