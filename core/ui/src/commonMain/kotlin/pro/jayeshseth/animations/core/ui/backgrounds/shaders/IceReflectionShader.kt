package pro.jayeshseth.animations.core.ui.backgrounds.shaders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.IceReflection
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.utils.DefaultPrefKeys

data class IceReflectionShader(override val speed: Float = 0.2f) : BackgroundType.Shader {
    override val key = DefaultPrefKeys.ICE_REFLECTION_KEY
    override val displayName = "IceReflection"
    override val previewColors = listOf(0xFFE0F7FA.toInt(), 0xFF80DEEA.toInt(), 0xFF4DD0E1.toInt())
    override fun withSpeed(speed: Float) = copy(speed = speed)

    @Composable
    override fun Content(modifier: Modifier) = IceReflection(modifier, speed)
}

@Preview
@Composable
private fun IceReflection(modifier: Modifier = Modifier, speed: Float = 0.2f) {
    Box(modifier.fillMaxSize().shaderBackground(IceReflection, speed))
}