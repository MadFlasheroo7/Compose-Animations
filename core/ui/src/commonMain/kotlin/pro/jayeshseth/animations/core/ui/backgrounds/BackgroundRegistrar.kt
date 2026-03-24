package pro.jayeshseth.animations.core.ui.backgrounds

import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.ui.backgrounds.canvas.DotGridCanvas
import pro.jayeshseth.animations.core.ui.backgrounds.images.CatImageBackground
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.AuroraShader
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.BlackCherryCosmosShader
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.BubbleRingsShader
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.GoldenMagmaShader
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.GradientFlowShader
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.HeatShader
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.IceReflectionShader
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.InkFlowShader
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.OilFlowShader
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.PurpleLiquidShader
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.StageShader
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.StripyShader

/**
 * Single registration point for all built-in background types.
 *
 * Called once at app startup (via `initializeApp()`) to populate the
 * [pro.jayeshseth.animations.core.model.BackgroundType.Shader], [pro.jayeshseth.animations.core.model.BackgroundType.Canvas], and [pro.jayeshseth.animations.core.model.BackgroundType.Image]
 * registries. After this call, the picker UI can enumerate [pro.jayeshseth.animations.core.model.BackgroundType.Shader.Companion.entries],
 * [pro.jayeshseth.animations.core.model.BackgroundType.Canvas.Companion.entries], and [pro.jayeshseth.animations.core.model.BackgroundType.Image.Companion.entries], and the
 * persistence layer can restore selections via `fromKey()`.
 */
object BackgroundRegistrar {
    /** Registers all shaders, canvas patterns, and image backgrounds. */
    fun register() {
        // Shaders
        BackgroundType.Shader.register(InkFlowShader())
        BackgroundType.Shader.register(BlackCherryCosmosShader())
        BackgroundType.Shader.register(OilFlowShader())
        BackgroundType.Shader.register(HeatShader())
        BackgroundType.Shader.register(GoldenMagmaShader())
        BackgroundType.Shader.register(BubbleRingsShader())
        BackgroundType.Shader.register(StageShader())
        BackgroundType.Shader.register(IceReflectionShader())
        BackgroundType.Shader.register(GradientFlowShader())
        BackgroundType.Shader.register(PurpleLiquidShader())
        BackgroundType.Shader.register(StripyShader())
        BackgroundType.Shader.register(AuroraShader())

        // Canvas
        BackgroundType.Canvas.register(DotGridCanvas())

        // Image backgrounds
        BackgroundType.Image.register(CatImageBackground)
    }
}