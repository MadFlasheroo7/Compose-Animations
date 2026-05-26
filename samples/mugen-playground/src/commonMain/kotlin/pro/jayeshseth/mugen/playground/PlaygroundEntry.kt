package pro.jayeshseth.mugen.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.Mugen
import pro.jayeshseth.mugen.MugenTheme
import pro.jayeshseth.mugen.components.MugenButton
import pro.jayeshseth.mugen.components.MugenText
import pro.jayeshseth.mugen.look.MugenLook
import pro.jayeshseth.mugen.look.haze.HazeLook
import pro.jayeshseth.mugen.look.material.MaterialLook
import pro.jayeshseth.mugen.sample.MugenSamplePlayground

/**
 * The playground's top-level composable. Hoists the active [MugenLook] as state and
 * wraps [MugenSamplePlayground] in a [MugenTheme] keyed to it. Tap the segmented
 * controls to swap Looks live.
 */
@Composable
fun PlaygroundEntry() {
    var look by remember { mutableStateOf<MugenLook>(HazeLook()) }

    MugenTheme(look = look) {
        Box(Modifier.fillMaxSize().background(look.colors.surface)) {
            MugenTheme
            Column(Modifier.fillMaxSize()) {
                LookPicker(
                    current = look,
                    onPick = { look = it },
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                )
                MugenSamplePlayground(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun LookPicker(
    current: MugenLook,
    onPick: (MugenLook) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        modifier = modifier,
    ) {
        LookChip(
            label = "Haze",
            selected = current.name == "Haze",
            onClick = { onPick(HazeLook()) },
        )
        LookChip(
            label = "Material",
            selected = current.name == "Material",
            onClick = { onPick(MaterialLook()) },
        )
    }
}

@Composable
private fun LookChip(label: String, selected: Boolean, onClick: () -> Unit) {
    MugenButton(
        onClick = onClick,
        enabled = !selected,
    ) {
        MugenText(if (selected) "$label · active" else label)
    }
}
