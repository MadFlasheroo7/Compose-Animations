package pro.jayeshseth.animations.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

@Composable
fun Toggler(
    title: String,
    hazeState: HazeState,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: TextStyle = LocalTextStyle.current
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = style)
        Spacer(modifier = Modifier.padding(8.dp))
        HazedSwitch(
            hazeState = hazeState,
            toggled = checked,
            onToggleChanged = onCheckedChanged,
            enabled = enabled
        )
    }

    Rebugger(
        composableName = "Toggler",
        trackMap = mapOf(
            "title" to title,
            "checked" to checked,
            "onCheckedChanged" to onCheckedChanged,
            "modifier" to modifier,
            "enabled" to enabled,
            "Arrangement.SpaceBetween" to Arrangement.SpaceBetween,
            "Alignment.CenterVertically" to Alignment.CenterVertically,
        ),
    )
}

@Preview
@Composable
private fun PreviewToggler() {
    var toggled by remember { mutableStateOf(true) }
    ShaderPreviewContent() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(500.dp)
                .hazeEffect(
                    state = it,
                    HazeStyle(
                        tint = HazeTint(Color.Black.copy(.4f)),
                        blurRadius = 50.dp,
                        noiseFactor = .1f,
                    )
                )
        ) {
            Toggler(
                title = "Toggler",
                hazeState = it,
                checked = toggled,
                onCheckedChanged = { toggled = it },
                enabled = true
            )
            Toggler(
                title = "Toggler",
                hazeState = it,
                checked = toggled,
                onCheckedChanged = { toggled = it },
                enabled = true
            )
        }
    }

}