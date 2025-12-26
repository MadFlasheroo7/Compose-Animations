package pro.jayeshseth.animations.shaders.screens.interstellarSpace

import androidx.compose.runtime.Immutable
import pro.jayeshseth.animations.core.model.round

@Immutable
class InterstellarShaderCode(
    val state: InterstellarSpaceState,
) {
    fun composeCode(): String {
        return """
            var touchPosition by remember { mutableStateOf(0f to 0f) }
            Box(
                Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures { change, _ ->
                            touchPosition = change.position.x to change.position.y
                        }
                    }
                    .drawWithCache {
                        val runtimeShader = RuntimeShader(MYSHADER)
                        val shaderBrush = ShaderBrush(runtimeShader)
                        runtimeShader.setFloatUniform("iResolution", size.width, size.height)
                        runtimeShader.setFloatUniform("iTime", time)
                        runtimeShader.setFloatUniform(
                           "iMouse",
                           touchPosition.first,
                           touchPosition.second
                        )
                        onDrawBehind { 
                            drawRect(shaderBrush)
                        }
                    }
            )
        """.trimIndent()
    }

    fun shaderCode(): String {
        return """
            // Star Nest by Pablo Roman Andrioli
            uniform float2 iResolution;
            uniform float iTime;        
            uniform float2 iMouse;      

            const int iterations = 17;
            const float formuparam = ${state.formuparam.round()};

            const int volsteps = 20;
            const float stepsize = ${state.stepsize};

            const float zoom = ${state.zoom};
            const float tile = ${state.tile};
            const float speed = ${state.speed};

            const float brightness = ${state.brightness};
            const float darkmatter = ${state.darkmatter};
            const float distfading = ${state.distFading};
            const float saturation = ${state.saturation};

            half4 main(float2 fragCoord) {
                float2 uv = fragCoord / iResolution - 0.5;
                uv.y *= iResolution.y / iResolution.x;
                float3 dir = float3(uv * zoom, 1.0);
                float time = iTime * speed + 0.25;

                float a1 = 0.5 + iMouse.x / iResolution.x * 2.0;
                float a2 = 0.8 + iMouse.y / iResolution.y * 2.0;
                float2x2 rot1 = float2x2(cos(a1), sin(a1), -sin(a1), cos(a1));
                float2x2 rot2 = float2x2(cos(a2), sin(a2), -sin(a2), cos(a2));
                dir.xz *= rot1;
                dir.xy *= rot2;
                float3 from = float3(1.0, 0.5, 0.5);
                from += float3(time * 2.0, time, -2.0);
                from.xz *= rot1;
                from.xy *= rot2;

                float s = 0.1;
                float fade = 1.0;
                float3 v = float3(0.0);
                for (int r = 0; r < volsteps; r++) {
                    float3 p = from + s * dir * 0.5;
                    p = abs(float3(tile) - mod(p, float3(tile * 2.0)));
                    float pa, a = pa = 0.0;
                    for (int i = 0; i < iterations; i++) {
                        p = abs(p) / dot(p, p) - formuparam;
                        a += abs(length(p) - pa);
                        pa = length(p);
                    }
                    float dm = max(0.0, darkmatter - a * a * 0.001);
                    a *= a * a;
                    if (r > 6) fade *= 1.0 - dm;
                    v += fade;
                    v += float3(s, s * s, s * s * s * s) * a * brightness * fade;
                    fade *= distfading;
                    s += stepsize;
                }
                v = mix(float3(length(v)), v, saturation);
                return half4(v * 0.01, 1.0);
            }
""".trimIndent()
    }
}