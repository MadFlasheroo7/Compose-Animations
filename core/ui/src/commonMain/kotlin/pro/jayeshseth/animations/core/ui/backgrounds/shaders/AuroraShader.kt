package pro.jayeshseth.animations.core.ui.backgrounds.shaders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.hypnoticcanvas.RuntimeEffect
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.Shader
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.utils.DefaultPrefKeys

/**
 * Custom SKSL shader producing an animated aurora borealis (northern lights) effect.
 *
 * Uses fractal Brownian motion (fBm) noise to warp UVs, then maps the resulting
 * values through a color palette transitioning from deep blue to green to teal to purple.
 * A vertical fade makes the aurora stronger near the top of the screen.
 *
 * This shader is **not** from the hypnoticcanvas library — it is a custom
 * SKSL program that plugs into the hypnoticcanvas runtime via [Shader].
 */
private object AuroraHcShader : Shader {
    override val name = "Aurora"
    override val authorName = "Compose-Animations"
    override val authorUrl = ""
    override val credit = ""
    override val license = ""
    override val licenseUrl = ""
    override val speedModifier = 0.3f

    override val sksl = """
        uniform float3 uResolution;
        uniform float uTime;

        // Simple hash-based noise
        float hash(float2 p) {
            float h = dot(p, float2(127.1, 311.7));
            return fract(sin(h) * 43758.5453123);
        }

        float noise(float2 p) {
            float2 i = floor(p);
            float2 f = fract(p);
            f = f * f * (3.0 - 2.0 * f);
            float a = hash(i);
            float b = hash(i + float2(1.0, 0.0));
            float c = hash(i + float2(0.0, 1.0));
            float d = hash(i + float2(1.0, 1.0));
            return mix(mix(a, b, f.x), mix(c, d, f.x), f.y);
        }

        float fbm(float2 p) {
            float v = 0.0;
            float a = 0.5;
            float2 shift = float2(100.0, 100.0);
            for (int i = 0; i < 5; i++) {
                v += a * noise(p);
                p = p * 2.0 + shift;
                a *= 0.5;
            }
            return v;
        }

        half4 main(float2 fragCoord) {
            float2 uv = fragCoord / uResolution.xy;
            float t = uTime * 0.4;

            // Warp UVs with flowing noise
            float2 q = float2(
                fbm(uv * 3.0 + float2(0.0, t * 0.3)),
                fbm(uv * 3.0 + float2(5.2, t * 0.2))
            );
            float2 r = float2(
                fbm(uv * 4.0 + q * 4.0 + float2(1.7, t * 0.15)),
                fbm(uv * 4.0 + q * 4.0 + float2(8.3, t * 0.25))
            );
            float f = fbm(uv * 2.0 + r * 1.5);

            // Aurora color palette: deep blue -> green -> teal -> purple
            float3 col = float3(0.0);
            col = mix(float3(0.02, 0.02, 0.1), float3(0.0, 0.6, 0.3), smoothstep(0.0, 0.5, f));
            col = mix(col, float3(0.0, 0.8, 0.6), smoothstep(0.3, 0.7, f));
            col = mix(col, float3(0.4, 0.1, 0.6), smoothstep(0.5, 0.9, f));

            // Vertical fade — aurora stronger near top
            float vFade = smoothstep(1.0, 0.2, uv.y);
            col *= 0.4 + 0.8 * vFade;

            // Subtle shimmer
            col += 0.03 * noise(uv * 20.0 + t);

            return half4(half3(col), 1.0);
        }
    """.trimIndent()

    override fun applyUniforms(
        runtimeEffect: RuntimeEffect,
        time: Float,
        width: Float,
        height: Float
    ) {
        runtimeEffect.setFloatUniform("uResolution", width, height, width / height)
        runtimeEffect.setFloatUniform("uTime", time)
    }
}

/**
 * [BackgroundType.Shader] implementation that renders the custom [AuroraHcShader] SKSL effect.
 *
 * @param speed Playback speed multiplier (default 0.2). Higher values make the
 *              aurora flow faster.
 */
data class AuroraShader(override val speed: Float = 0.2f) : BackgroundType.Shader {
    override val key = DefaultPrefKeys.AURORA_KEY
    override val displayName = "Aurora"
    override val previewColors = listOf(0xFF020210.toInt(), 0xFF009966.toInt(), 0xFF660099.toInt())
    override fun withSpeed(speed: Float) = copy(speed = speed)

    @Composable
    override fun Content(modifier: Modifier) = AuroraShader(modifier, speed)
}

@Preview
@Composable
private fun AuroraShader(modifier: Modifier = Modifier, speed: Float = 0.2f) {
    Box(modifier.fillMaxSize().shaderBackground(AuroraHcShader, speed))
}