package pro.jayeshseth.animations.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger

@Composable
fun Toggler(
    title: String,
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
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChanged,
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