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
        val CLOUD = """
            uniform vec2 iResolution;
            uniform float iTime;
            
            vec3 hsv(float h, float s, float v){
                vec4 t = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
                vec3 p = abs(fract(vec3(h) + t.xyz) * 6.0 - vec3(t.w));
                return v * mix(vec3(t.x), clamp(p - vec3(t.x), 0.0, 1.0), s);
            }

            vec4 main(vec2 FC) {
              float e=0,R=0,t=iTime,s;
              vec2 r = iResolution.xy;
              vec3 q=vec3(0,0,-1), p, d=vec3((FC.xy-.5*r)/r.y,.7);
              vec4 o=vec4(0);
              for(float i=0;i<100;++i) {
                o.rgb+=hsv(.1,e*.4,e/1e2)+.005;
                p=q+=d*max(e,.02)*R*.3;
                float py = (p.x == 0 && p.y == 0) ? 1 : p.y;
                p=vec3(log(R=length(p))-t,e=asin(-p.z/R)-1.,atan(p.x,py)+t/3.);
                s=1;
                for(int z=1; z<=9; ++z) {
                  e+=cos(dot(sin(p*s),cos(p.zxy*s)))/s;
                  s+=s;
                }
                i>50.?d/=-d:d;
              }
              return o;
            }
        """.trimIndent()

        val HOUR = """
            uniform vec2 iResolution;
            uniform float iTime;

            vec4 main(vec2 FC) {
                vec4 o = vec4(0.0);
                
                // Safety: Prevent division by zero if iResolution is 0 on the first frame
                vec2 res = max(iResolution.xy, vec2(1.0));
                
                vec2 u = FC.xy * 2.0 - res;
                vec2 p = vec2(0.0);
                vec2 c = vec2(0.0);
                float a;

                for (float i = 0.0; i < 400.0; i++) {
                    a = i / 200.0 - 1.0;
                    
                    // max() prevents negative square roots (NaN crashes on strict compilers)
                    p = cos(i * 2.4 + iTime + vec2(0.0, 11.0)) * sqrt(0.0 - a * a);
                    
                    c = u / res.y + vec2(p.x, a) / (p.y + 2.0);
                    
                    // Instead of dividing by 30000.0 (3e4), we multiply by 0.0000333 to prevent overflow.
                    // We also clamp dot(c,c) to a tiny value so the dots stay sharp, but never hit Infinity.
                    float glow = 0.0000333 / max(dot(c, c), 0.000001);
                    
                    o += (cos(i + vec4(0.0, 2.0, 4.0, 0.0)) + 1.0) * glow * (1.0 - p.y);
                }
                
                return o;
            }
        """.trimIndent()

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
