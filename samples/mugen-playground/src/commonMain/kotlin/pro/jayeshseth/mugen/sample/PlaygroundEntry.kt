package pro.jayeshseth.mugen.sample

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.MugenTheme
import pro.jayeshseth.mugen.components.MugenButton
import pro.jayeshseth.mugen.components.MugenText
import pro.jayeshseth.mugen.look.MugenLook
import pro.jayeshseth.mugen.look.haze.HazeLook
import pro.jayeshseth.mugen.look.material.MaterialLook
import pro.jayeshseth.mugen.look.plain.PlainLook
import pro.jayeshseth.mugen.renderers.MugenRendererSet
import pro.jayeshseth.mugen.renderers.haze.HazeRendererSet
import pro.jayeshseth.mugen.renderers.material.MaterialRendererSet
import pro.jayeshseth.mugen.renderers.plain.PlainRenderers

/**
 * The playground's top-level composable. Hoists the active [MugenLook] and [MugenRendererSet] as state and
 * wraps [MugenSamplePlayground] in a [MugenTheme] keyed to them. Tap the segmented
 * controls to swap Looks and their corresponding RendererSets live.
 */
@Composable
fun PlaygroundEntry() {
    var look by remember { mutableStateOf<MugenLook>(PlainLook()) }
    var renderers by remember { mutableStateOf<MugenRendererSet>(PlainRenderers) }

    val animatedBgColor by animateColorAsState(
        targetValue = look.colors.surface,
        animationSpec = tween(durationMillis = 500)
    )

    MugenTheme(look = look, renderers = renderers) {
        Box(Modifier.systemBarsPadding().fillMaxSize().background(animatedBgColor)) {
            Column(Modifier.fillMaxSize()) {
                LookPicker(
                    current = look,
                    onPick = { newLook, newRenderers ->
                        look = newLook
                        renderers = newRenderers
                    },
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
    onPick: (MugenLook, MugenRendererSet) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        modifier = modifier,
    ) {
        LookChip(
            label = "Plain",
            selected = current.name == "Plain",
            onClick = { onPick(PlainLook(), PlainRenderers) },
        )
        LookChip(
            label = "Haze",
            selected = current.name == "Haze",
            onClick = {
                val hl = HazeLook()
                onPick(hl, HazeRendererSet(hl))
            },
        )
        LookChip(
            label = "Material",
            selected = current.name == "Material",
            onClick = {
                val ml = MaterialLook()
                onPick(ml, MaterialRendererSet(ml))
            },
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
