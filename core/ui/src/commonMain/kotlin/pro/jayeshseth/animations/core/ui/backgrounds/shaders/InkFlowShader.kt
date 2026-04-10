package pro.jayeshseth.animations.core.ui.backgrounds.shaders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.utils.DefaultPrefKeys

data class InkFlowShader(override val speed: Float = 0.2f) : BackgroundType.Shader {
    override val key = DefaultPrefKeys.INK_FLOW_KEY
    override val displayName = "InkFlow"
    override val previewColors = listOf(0xFF1A1A2E.toInt(), 0xFF16213E.toInt(), 0xFF0F3460.toInt())
    override fun withSpeed(speed: Float) = copy(speed = speed)

    @Composable
    override fun Content(modifier: Modifier) = InkFlow(modifier, speed)
}

@Preview
@Composable
private fun InkFlow(modifier: Modifier = Modifier, speed: Float = 0.2f) {
    Box(modifier.fillMaxSize().shaderBackground(InkFlow, speed))
}