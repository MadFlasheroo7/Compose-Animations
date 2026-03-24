package pro.jayeshseth.animations.core.ui.backgrounds.shaders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.Stage
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.utils.DefaultPrefKeys

data class StageShader(override val speed: Float = 0.2f) : BackgroundType.Shader {
    override val key = DefaultPrefKeys.STAGE_KEY
    override val displayName = "Stage"
    override val previewColors = listOf(0xFF1A0033.toInt(), 0xFF330066.toInt(), 0xFF4D0099.toInt())
    override fun withSpeed(speed: Float) = copy(speed = speed)

    @Composable
    override fun Content(modifier: Modifier) = Stage(modifier, speed)
}

@Preview
@Composable
private fun Stage(modifier: Modifier = Modifier, speed: Float = 0.2f) {
    Box(modifier.fillMaxSize().shaderBackground(Stage, speed))
}