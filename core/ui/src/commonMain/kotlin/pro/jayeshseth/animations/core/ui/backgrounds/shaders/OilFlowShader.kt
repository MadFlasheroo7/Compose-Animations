package pro.jayeshseth.animations.core.ui.backgrounds.shaders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.OilFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.utils.DefaultPrefKeys

data class OilFlowShader(override val speed: Float = 0.2f) : BackgroundType.Shader {
    override val key = DefaultPrefKeys.OIL_FLOW_KEY
    override val displayName = "OilFlow"
    override val previewColors = listOf(0xFF0D0D0D.toInt(), 0xFF1A3A1A.toInt(), 0xFF2D1B4E.toInt())
    override fun withSpeed(speed: Float) = copy(speed = speed)

    @Composable
    override fun Content(modifier: Modifier) = OilFlow(modifier, speed)
}

@Preview
@Composable
private fun OilFlow(modifier: Modifier = Modifier, speed: Float = 0.2f) {
    Box(modifier.fillMaxSize().shaderBackground(OilFlow, speed))
}