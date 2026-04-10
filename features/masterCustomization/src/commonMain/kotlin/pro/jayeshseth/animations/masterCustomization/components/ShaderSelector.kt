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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.launch
import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.InkFlowShader
import pro.jayeshseth.animations.core.ui.components.DropDownTemplate
import pro.jayeshseth.animations.core.ui.components.SliderTemplate
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationRepository
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationState

private enum class BackgroundCategory(val label: String) {
    Shader("Shader"),
    Canvas("Canvas"),
    Image("Image"),
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BackgroundCustomizationContent(
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    val repo = LocalCustomizationRepository.current
    val state = LocalCustomizationState.current
    val scope = rememberCoroutineScope()

    val currentCategory = remember(state.backgroundType) {
        when (state.backgroundType) {
            is BackgroundType.Shader -> BackgroundCategory.Shader
            is BackgroundType.Canvas -> BackgroundCategory.Canvas
            is BackgroundType.Image -> BackgroundCategory.Image
        }
    }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        DropDownTemplate(
            hazeState = hazeState,
            value = currentCategory.label,
            expanded = expanded,
            onExpandedChange = { expanded = it },
            title = { Text("Background Type") },
            onDismissRequest = { expanded = false },
        ) {
            BackgroundCategory.entries.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.label) },
                    onClick = {
                        expanded = false
                        val newBackground = when (category) {
                            BackgroundCategory.Shader -> {
                                val current = state.backgroundType
                                if (current is BackgroundType.Shader) current
                                else InkFlowShader()
                            }

                            BackgroundCategory.Canvas -> {
                                val current = state.backgroundType
                                if (current is BackgroundType.Canvas) current
                                else BackgroundType.Canvas.entries.first()
                            }

                            BackgroundCategory.Image -> {
                                val current = state.backgroundType
                                if (current is BackgroundType.Image) current
                                else BackgroundType.Image.entries.first()
                            }
                        }
                        scope.launch { repo?.updateBackground(newBackground) }
                    }
                )
            }
        }

        SliderTemplate(
            title = "Background Blur",
            hazeState = hazeState,
            value = { state.backgroundBlur.value },
            step = { 0 },
            onValueChange = { scope.launch { repo?.updateBackgroundBlur(it.dp) } },
            valueRange = 0f..20f,
            roundToInt = false,
            onIncrement = {
                val newBlur = (state.backgroundBlur + 0.05.dp)
                scope.launch { repo?.updateBackgroundBlur(newBlur) }
            },
            onDecrement = {
                val newBlur = (state.backgroundBlur - 0.05.dp)
                scope.launch { repo?.updateBackgroundBlur(newBlur) }
            },
        )

//        Spacer(Modifier.height(24.dp))

        when (val bg = state.backgroundType) {
            is BackgroundType.Shader -> {
                ShaderCustomizationContent(
                    hazeState = hazeState,
                    shaderBackground = bg,
                    onUpdateBackground = { scope.launch { repo?.updateBackground(it) } }
                )
            }

            is BackgroundType.Canvas -> {
                CanvasCustomizationContent(
                    canvasBackground = bg,
                    onUpdateBackground = { scope.launch { repo?.updateBackground(it) } }
                )
            }

            is BackgroundType.Image -> {
                ImageCustomizationContent(
                    imageBackground = bg,
                    onUpdateBackground = { scope.launch { repo?.updateBackground(it) } }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ShaderCustomizationContent(
    hazeState: HazeState,
    shaderBackground: BackgroundType.Shader,
    onUpdateBackground: (BackgroundType.Shader) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = "Shader",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BackgroundType.Shader.entries.forEach { shaderTemplate ->
                ShaderCard(
                    name = shaderTemplate.displayName,
                    colors = shaderTemplate.previewColors.map { Color(it) },
                    isSelected = shaderBackground.key == shaderTemplate.key,
                    onClick = {
                        onUpdateBackground(shaderTemplate.withSpeed(shaderBackground.speed))
                    }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        SliderTemplate(
            title = "Speed",
            hazeState = hazeState,
            value = { shaderBackground.speed },
            step = { 0 },
            onValueChange = { onUpdateBackground(shaderBackground.withSpeed(it)) },
            valueRange = 0.05f..10f,
            roundToInt = false,
            onIncrement = {
                val newSpeed = (shaderBackground.speed + 0.05f).coerceAtMost(1f)
                onUpdateBackground(shaderBackground.withSpeed(newSpeed))
            },
            onDecrement = {
                val newSpeed = (shaderBackground.speed - 0.05f).coerceAtLeast(0.05f)
                onUpdateBackground(shaderBackground.withSpeed(newSpeed))
            },
        )
    }
}

@Composable
private fun ShaderCard(
    name: String,
    colors: List<Color>,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth(0.45f)
            .clip(shape)
            .background(
                Brush.linearGradient(colors),
                shape
            )
            .then(
                if (isSelected) {
                    Modifier.border(2.dp, Color.White, shape)
                } else {
                    Modifier.border(1.dp, Color.White.copy(alpha = 0.2f), shape)
                }
            )
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Text(
            text = name,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = Color.White,
            textAlign = TextAlign.Start,
        )
    }
}

private val canvasPresetColors = listOf(
    0xFF00FFFF.toInt(),
    0xFFFF4081.toInt(),
    0xFF69F0AE.toInt(),
    0xFFFFD740.toInt(),
    0xFFE040FB.toInt(),
    0xFF448AFF.toInt(),
    0xFFFF6E40.toInt(),
    0xFF40C4FF.toInt(),
    0xFFB2FF59.toInt(),
    0xFFFF5252.toInt(),
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CanvasCustomizationContent(
    canvasBackground: BackgroundType.Canvas,
    onUpdateBackground: (BackgroundType.Canvas) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = "Pattern",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BackgroundType.Canvas.entries.forEach { canvasTemplate ->
                ShaderCard(
                    name = canvasTemplate.displayName,
                    colors = canvasTemplate.previewColors.map { Color(it) },
                    isSelected = canvasBackground.key == canvasTemplate.key,
                    onClick = {
                        onUpdateBackground(canvasTemplate.withColor(canvasBackground.colorArgb))
                    }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Color",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            canvasPresetColors.forEach { argb ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(argb), CircleShape)
                        .then(
                            if (argb == canvasBackground.colorArgb) {
                                Modifier.border(3.dp, Color.White, CircleShape)
                            } else {
                                Modifier.border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                            }
                        )
                        .clickable {
                            onUpdateBackground(canvasBackground.withColor(argb))
                        }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ImageCustomizationContent(
    imageBackground: BackgroundType.Image,
    onUpdateBackground: (BackgroundType.Image) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = "Image",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BackgroundType.Image.entries.forEach { imageTemplate ->
                ShaderCard(
                    name = imageTemplate.displayName,
                    colors = imageTemplate.previewColors.map { Color(it) },
                    isSelected = imageBackground.key == imageTemplate.key,
                    onClick = { onUpdateBackground(imageTemplate) }
                )
            }
        }
    }
}
