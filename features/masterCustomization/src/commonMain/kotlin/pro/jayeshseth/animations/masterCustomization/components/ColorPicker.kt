package pro.jayeshseth.animations.masterCustomization.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.launch
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.theme.Blue400
import pro.jayeshseth.animations.core.ui.theme.BlueGrey300
import pro.jayeshseth.animations.core.ui.theme.Cyan
import pro.jayeshseth.animations.core.ui.theme.DeepOrange400
import pro.jayeshseth.animations.core.ui.theme.DeepPurple400
import pro.jayeshseth.animations.core.ui.theme.Green400
import pro.jayeshseth.animations.core.ui.theme.Indigo400
import pro.jayeshseth.animations.core.ui.theme.LightBlue400
import pro.jayeshseth.animations.core.ui.theme.LightGreen400
import pro.jayeshseth.animations.core.ui.theme.Lime400
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationRepository
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationState
import pro.jayeshseth.animations.core.ui.theme.Orange400
import pro.jayeshseth.animations.core.ui.theme.Pink400
import pro.jayeshseth.animations.core.ui.theme.Pink80
import pro.jayeshseth.animations.core.ui.theme.Purple400
import pro.jayeshseth.animations.core.ui.theme.Purple80
import pro.jayeshseth.animations.core.ui.theme.PurpleGrey80
import pro.jayeshseth.animations.core.ui.theme.Red400
import pro.jayeshseth.animations.core.ui.theme.Teal400
import pro.jayeshseth.animations.core.ui.theme.Yellow400

private val presetColors = listOf(
    Purple80.toArgb(),
    PurpleGrey80.toArgb(),
    Pink80.toArgb(),
    Red400.toArgb(),
    Pink400.toArgb(),
    Purple400.toArgb(),
    DeepPurple400.toArgb(),
    Indigo400.toArgb(),
    Blue400.toArgb(),
    LightBlue400.toArgb(),
    Cyan.toArgb(),
    Teal400.toArgb(),
    Green400.toArgb(),
    LightGreen400.toArgb(),
    Lime400.toArgb(),
    Yellow400.toArgb(),
    Orange400.toArgb(),
    DeepOrange400.toArgb(),
    BlueGrey300.toArgb(),
    Color.White.toArgb(),
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThemeCustomizationContent(
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    val repo = LocalCustomizationRepository.current
    val state = LocalCustomizationState.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        ColorSection(
            title = "Heading",
            selectedArgb = state.headingColorArgb,
            onColorSelected = { scope.launch { repo?.updateHeadingColor(it) } }
        )

        Spacer(Modifier.height(16.dp))

        ColorSection(
            title = "Primary",
            selectedArgb = state.primaryColorArgb,
            onColorSelected = { scope.launch { repo?.updatePrimaryColor(it) } }
        )

        Spacer(Modifier.height(16.dp))

        ColorSection(
            title = "Primary Button Accent",
            selectedArgb = state.primaryButtonAccentColorArgb,
            onColorSelected = { scope.launch { repo?.updatePrimaryButtonAccentColor(it) } }
        )

        ColorSection(
            title = "Button Accent",
            selectedArgb = state.buttonAccentColorArgb,
            onColorSelected = { scope.launch { repo?.updateButtonAccentColor(it) } }
        )

        ColorSection(
            title = "Accent",
            selectedArgb = state.accentColorArgb,
            onColorSelected = { scope.launch { repo?.updateAccentColor(it) } }
        )

        Spacer(Modifier.height(24.dp))

        InteractiveButton(
            hazeState = hazeState,
            text = "Reset to Default",
            onClick = {
                scope.launch { repo?.resetToDefaults() }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColorSection(
    title: String,
    selectedArgb: Int,
    onColorSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            presetColors.forEach { argb ->
                ColorSwatch(
                    argb = argb,
                    isSelected = argb == selectedArgb,
                    onClick = { onColorSelected(argb) }
                )
            }
        }
    }
}

@Composable
private fun ColorSwatch(
    argb: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(argb), CircleShape)
            .then(
                if (isSelected) {
                    Modifier.border(3.dp, Color.White, CircleShape)
                } else {
                    Modifier.border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                }
            )
            .clickable(onClick = onClick)
    )
}
