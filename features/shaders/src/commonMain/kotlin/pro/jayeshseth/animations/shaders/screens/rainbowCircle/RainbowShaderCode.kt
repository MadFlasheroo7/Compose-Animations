package pro.jayeshseth.animations.shaders.screens.rainbowCircle

import androidx.compose.runtime.Stable
import pro.jayeshseth.animations.core.model.round
import pro.jayeshseth.animations.core.model.toExponent

//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Stable
class RainbowShaderCode(
    val state: RainbowCircleState,
//    iterationLevel: ShaderIterationLevel,
) {
//    val iteration = if (iterationLevel == ShaderIterationLevel.HIGH) "4e2" else "2e2"
    val iteration = ""
    fun composeCode(): String {
        return """
            Box(
                Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        val runtimeShader = RuntimeShader(MYSHADER)
                        val shaderBrush = ShaderBrush(runtimeShader)
                        runtimeShader.setFloatUniform("iResolution", size.width, size.height)
                        runtimeShader.setFloatUniform("iTime", time)
                        onDrawBehind { 
                            drawRect(shaderBrush)
                        }
                    }
            )
        """.trimIndent()
    }

    fun shaderCode(): String {
        return """
            uniform float2 iResolution;
            uniform float iTime;
        
            half4 main(in float2 fragCoord) {
                half4 o = half4(0);
                float2 p = float2(0), c = p;
                float2 u = fragCoord.xy * 2.0 - iResolution.xy; 
                float a;
        
                for (float i=0; i<$iteration; i++) {
                    a = i/${state.layers.toExponent()}-1.;
                    p = cos(i * ${state.pattern} + iTime + float2(0,11)) * sqrt(${state.expand} - a * a);
                    c = u / iResolution.y + float2(p.x, a) / (p.y + ${state.size});
                    o += (cos(i + half4(${state.red.round()}, ${state.green.round()}, ${state.blue.round()}, ${state.alpha.round()})) + 1.) / dot(c,c) * (1. - p.y) / ${state.brightness.toExponent()};
                }
                return o;
            }
        """.trimIndent()
    }
}