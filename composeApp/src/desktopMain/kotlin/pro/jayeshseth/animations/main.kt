package pro.jayeshseth.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import org.jetbrains.skia.ImageFilter
import org.jetbrains.skia.Paint
import org.jetbrains.skia.RuntimeEffect
import org.jetbrains.skia.RuntimeShaderBuilder
import pro.jayeshseth.animations.core.model.CustomizationState
import pro.jayeshseth.animations.core.navigation.rememberNavigator
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationRepository
import pro.jayeshseth.animations.core.ui.theme.LocalCustomizationState
import pro.jayeshseth.animations.core.ui.theme.LocalEasterEggRepository
import pro.jayeshseth.animations.core.utils.rememberCustomizationRepository
import pro.jayeshseth.animations.core.utils.rememberEasterEggRepository
import pro.jayeshseth.animations.navigation.LandingRoutes
import pro.jayeshseth.animations.navigation.NavGraph
import pro.jayeshseth.animations.navigation.initializeApp
import pro.jayeshseth.animations.ui.screens.JELLY_SKSL

/**
 * The main entry point of the Compose Animations application.
 */
fun main() = application {
    initializeApp()
    val backStack = rememberNavigator(LandingRoutes.Home)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Animations",
        onKeyEvent = { keyEvent ->
            if (keyEvent.type == KeyEventType.KeyDown &&
                keyEvent.key == Key.DirectionLeft &&
                keyEvent.isAltPressed
            ) {
                backStack.navBack()
                true
            } else {
                false
            }
        },
    ) {
        val repo = rememberCustomizationRepository()
        val easterEggRepo = rememberEasterEggRepository()
        val customizationState by repo.state.collectAsState(initial = CustomizationState())
        CompositionLocalProvider(
            LocalCustomizationState provides customizationState,
            LocalCustomizationRepository provides repo,
            LocalEasterEggRepository provides easterEggRepo,
        ) {
            AnimationsTheme {
                NavGraph(backStack) { }
            }
        }
    }
}

@Composable
fun CrossPlatformJellyButton() {
    val scope = rememberCoroutineScope()
    val bounceAnim = remember() { Animatable(0f) }
    val interactionSource by remember { mutableStateOf(MutableInteractionSource()) }

    val isHover by interactionSource.collectIsHoveredAsState()

    LaunchedEffect(interactionSource, isHover) {
        if (isHover) {
            bounceAnim.snapTo(0f)
            bounceAnim.animateTo(5f, spring(dampingRatio = 0.6f, stiffness = 400f))
            bounceAnim.animateTo(0f)
        } else {
            bounceAnim.animateTo(0f)
        }
    }

    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
        repeat(3) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    // Use our custom Skia modifier here
                    .jellyEffect(bounceAnim.value)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        scope.launch {
                            bounceAnim.animateTo(0f)
                            bounceAnim.animateTo(5f, spring(dampingRatio = 0.6f, stiffness = 400f))
                            bounceAnim.animateTo(0f)
                        }
                    }
                    // ... size and background
                    .size(200.dp, 60.dp)
                    .background(Color(0xFF6200EE), RoundedCornerShape(12.dp))
            ) {
                Text("CMP JELLY", color = Color.White)
            }
        }
    }
}

fun Modifier.jellyEffect(progress: Float): Modifier = this.drawWithCache {
    // 1. Compile the Shader (Once per size change/cache)
    // We use 'makeForShader' because we want to run a shader program
    val runtimeEffect = RuntimeEffect.makeForShader(JELLY_SKSL)

    // 2. Create the Builder
    val shaderBuilder = RuntimeShaderBuilder(runtimeEffect)

    onDrawWithContent {
        // 3. Update Uniforms (Every frame)
        shaderBuilder.uniform("resolution", size.width, size.height)
        shaderBuilder.uniform("progress", progress)

        // 4. Create the ImageFilter
        // 'content' is the name of the uniform in SKSL
        // passing 'null' as the input connects it to the source graphic (the button)
        val imageFilter = ImageFilter.makeRuntimeShader(
            runtimeShaderBuilder = shaderBuilder,
            shaderName = "content",
            input = null
        )

        // 5. Access the Native Skia Canvas
        drawIntoCanvas { canvas ->
            val nativeCanvas = canvas.nativeCanvas

            // 6. Create a Skia Paint with our filter
            val paint = Paint().apply {
                this.imageFilter = imageFilter
            }

            // 7. Save a Layer -> Draw Content -> Restore
            // This captures the content, runs it through the filter, and puts it on screen
            nativeCanvas.saveLayer(null, paint)
            drawContent()
            nativeCanvas.restore()
        }
    }
}
