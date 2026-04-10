package pro.jayeshseth.animations.core.ui.backgrounds.shaders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.GoldenMagma
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.utils.DefaultPrefKeys

data class GoldenMagmaShader(override val speed: Float = 0.2f) : BackgroundType.Shader {
    override val key = DefaultPrefKeys.GOLDEN_MAGMA_KEY
    override val displayName = "GoldenMagma"
    override val previewColors = listOf(0xFF8B4513.toInt(), 0xFFDAA520.toInt(), 0xFFFF8C00.toInt())
    override fun withSpeed(speed: Float) = copy(speed = speed)

    @Composable
    override fun Content(modifier: Modifier) = GoldenMagma(modifier, speed)
}

@Preview
@Composable
private fun GoldenMagma(modifier: Modifier = Modifier, speed: Float = 0.2f) {
    Box(modifier.fillMaxSize().shaderBackground(GoldenMagma, speed))
}