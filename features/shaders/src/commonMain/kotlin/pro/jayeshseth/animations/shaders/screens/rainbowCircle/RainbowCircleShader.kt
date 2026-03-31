package pro.jayeshseth.animations.shaders.screens.rainbowCircle

import com.mikepenz.hypnoticcanvas.RuntimeEffect
import com.mikepenz.hypnoticcanvas.shaders.Shader

/**
 * CMP-compatible RainbowCircle shader using HypnoticCanvas.
 *
 * Mutable properties are updated from the composable each frame via [applyUniforms].
 * Recreate the instance when [reduceDots] changes.
 */
class RainbowCircleShader(
    reduceDots: Boolean = true
) : Shader {
    override val name = "RainbowCircle"
    override val authorName = "Compose-Animations"
    override val authorUrl = ""
    override val credit = ""
    override val license = ""
    override val licenseUrl = ""
    override val speedModifier = 1f

    // Runtime-configurable uniforms
    var pattern: Float = 5.3f
    var expand: Float = 1.0f
    var red: Float = 0f
    var green: Float = 2.0f
    var blue: Float = 4.0f
    var shaderAlpha: Float = 1.0f
    var layers: Float = if (reduceDots) 100f else 200f
    var brightness: Float = 30000f
    var shaderSize: Float = 2f

    override val sksl = if (reduceDots) SHADER_LOW_ITERATIONS else SHADER_HIGH_ITERATIONS

    fun updateUniforms(state: RainbowCircleState) {
        pattern = state.pattern
        expand = state.expand
        brightness = state.brightness
        layers = state.layers
        shaderSize = state.size
        red = state.red
        green = state.green
        blue = state.blue
        shaderAlpha = state.alpha
    }

    override fun applyUniforms(
        runtimeEffect: RuntimeEffect,
        time: Float,
        width: Float,
        height: Float
    ) {
        runtimeEffect.setFloatUniform("iResolution", width, height)
        runtimeEffect.setFloatUniform("iTime", time)
        runtimeEffect.setFloatUniform("pattern", pattern)
        runtimeEffect.setFloatUniform("expand", expand)
        runtimeEffect.setFloatUniform("red", red)
        runtimeEffect.setFloatUniform("green", green)
        runtimeEffect.setFloatUniform("blue", blue)
        runtimeEffect.setFloatUniform("alpha", shaderAlpha)
        runtimeEffect.setFloatUniform("layers", layers)
        runtimeEffect.setFloatUniform("bright", brightness)
        runtimeEffect.setFloatUniform("size", shaderSize)
    }

    companion object {
        // SKSL versions of the original AGSL shaders (removed `in` keyword from main signature)
        val SHADER_HIGH_ITERATIONS = """
            uniform float2 iResolution;
            uniform float iTime;
            uniform float pattern,expand,red,green,blue,alpha,layers,bright,size;

            half4 main(float2 fragCoord) {
                half4 o = half4(0);
                float2 p = float2(0), c = p;
                float2 u = fragCoord.xy * 2.0 - iResolution.xy;
                float a;
                float resScale = iResolution.y / 1080.0;
                float resScale2 = resScale * resScale;

                for (float i=0; i<4e2; i++) {
                    a = i/layers - 1.;
                    p = cos(i * pattern + iTime + float2(0,11)) * sqrt(expand - a * a);
                    c = u / iResolution.y + float2(p.x, a) / (p.y + size);
                    o += (cos(i + half4(red,blue,green,alpha)) + 1.) / dot(c,c) * (1. - p.y) / bright * resScale2;
                }
                return o;
            }
        """.trimIndent()

        val SHADER_LOW_ITERATIONS = """
            uniform float2 iResolution;
            uniform float iTime;
            uniform float pattern,expand,red,green,blue,alpha,layers,bright,size;

            half4 main(float2 fragCoord) {
                half4 o = half4(0);
                float2 p = float2(0), c = p;
                float2 u = fragCoord.xy * 2.0 - iResolution.xy;
                float a;
                float resScale = iResolution.y / 1080.0;
                float resScale2 = resScale * resScale;

                for (float i=0; i<2e2; i++) {
                    a = i/layers - 1.;
                    p = cos(i * pattern + iTime + float2(0,11)) * sqrt(expand - a * a);
                    c = u / iResolution.y + float2(p.x, a) / (p.y + size);
                    o += (cos(i + half4(red,blue,green,alpha)) + 1.) / dot(c,c) * (1. - p.y) / bright * resScale2;
                }
                return o;
            }
        """.trimIndent()
    }
}
