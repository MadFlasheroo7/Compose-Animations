package pro.jayeshseth.animations.ui.shaders.rainbowCircle

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Size
import pro.jayeshseth.animations.core.model.ComposeFriendlyFloat

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
enum class ShaderIterationLevel(val shader: String) {
    HIGH(RainbowCircleShader.SHADER_HIGH_ITERATIONS),
    LOW(RainbowCircleShader.SHADER_LOW_ITERATIONS)
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Stable
class RainbowCircleShader(
    iterationLevel: ShaderIterationLevel = ShaderIterationLevel.LOW
) : RuntimeShader(iterationLevel.shader) {

    fun updateResolution(size: Size) {
        setFloatUniform(
            /* uniformName = */ "iResolution",
            /* value1 = */ size.width,
            /* value2 = */ size.height,
        )
    }

    fun updateTime(time: ComposeFriendlyFloat) {
        setFloatUniform(
            /* uniformName = */ "iTime",
            /* value = */ time(),
        )
    }

    fun updateColor(
        red: ComposeFriendlyFloat,
        green: ComposeFriendlyFloat,
        blue: ComposeFriendlyFloat,
        alpha: ComposeFriendlyFloat,
    ) {
        setFloatUniform(
            /* uniformName = */ "red",
            /* value = */ red(),
        )
        setFloatUniform(
            /* uniformName = */ "green",
            /* value = */ green(),
        )
        setFloatUniform(
            /* uniformName = */ "blue",
            /* value = */ blue(),
        )
        setFloatUniform(
            /* uniformName = */ "alpha",
            /* value = */ alpha(),
        )
    }

    fun updateSize(size: ComposeFriendlyFloat) {
        setFloatUniform(
            /* uniformName = */ "size",
            /* value = */ size(),
        )
    }

    fun updateLayers(layers: ComposeFriendlyFloat) {
        setFloatUniform(
            /* uniformName = */ "layers",
            /* value = */ layers(),
        )
    }

    fun updateBrightness(brightness: ComposeFriendlyFloat) {
        setFloatUniform(
            /* uniformName = */ "bright",
            /* value = */ brightness(),
        )
    }

    fun updatePattern(pattern: ComposeFriendlyFloat) {
        setFloatUniform(
            /* uniformName = */ "pattern",
            /* value = */ pattern(),
        )
    }

    fun updateExpand(expand: ComposeFriendlyFloat) {
        setFloatUniform(
            /* uniformName = */ "expand",
            /* value = */ expand(),
        )
    }

    companion object {
        val SHADER_HIGH_ITERATIONS =
            """
            uniform float2 iResolution;
            uniform float iTime;
            uniform float pattern,expand,red,green,blue,alpha,layers,bright,size;
        
            half4 main(in float2 fragCoord) {
                half4 o = half4(0);
                float2 p = float2(0), c = p;
                float2 u = fragCoord.xy * 2.0 - iResolution.xy; 
                float a;
        
            for (float i=0; i<4e2; i++) {
                a = i/layers - 1.;
                p = cos(i * pattern + iTime + float2(0,11)) * sqrt(expand - a * a);
                c = u / iResolution.y + float2(p.x, a) / (p.y + size);
                o += (cos(i + half4(red,blue,green,alpha)) + 1.) / dot(c,c) * (1. - p.y) / bright;
            }
            return o;
        }
            """.trimIndent()

        val SHADER_LOW_ITERATIONS =
            """
            uniform float2 iResolution;
            uniform float iTime;
            uniform float pattern,expand,red,green,blue,alpha,layers,bright,size;
        
            half4 main(in float2 fragCoord) {
                half4 o = half4(0);
                float2 p = float2(0), c = p;
                float2 u = fragCoord.xy * 2.0 - iResolution.xy; 
                float a;
        
            for (float i=0; i<2e2; i++) {
                a = i/layers - 1.;
                p = cos(i * pattern + iTime + float2(0,11)) * sqrt(expand - a * a);
                c = u / iResolution.y + float2(p.x, a) / (p.y + size);
                o += (cos(i + half4(red,blue,green,alpha)) + 1.) / dot(c,c) * (1. - p.y) / bright;
            }
            return o;
        }
            """.trimIndent()
    }
}