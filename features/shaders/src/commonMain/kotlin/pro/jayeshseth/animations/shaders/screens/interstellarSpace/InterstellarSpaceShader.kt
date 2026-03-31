package pro.jayeshseth.animations.shaders.screens.interstellarSpace

//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//@Stable
//class InterstellarSpaceShader() : RuntimeShader(shader) {
//
//    fun updateResolution(size: Size) {
//        setFloatUniform(
//            /* uniformName = */ "iResolution",
//            /* value1 = */ size.width,
//            /* value2 = */ size.height,
//        )
//    }
//
//    fun updateTime(time: ComposeFriendlyFloat) {
//        setFloatUniform(
//            /* uniformName = */ "iTime",
//            /* value = */ time(),
//        )
//    }
//
//    fun updateMouse(positionY: ComposeFriendlyFloat, positionX: ComposeFriendlyFloat) {
//        setFloatUniform(
//            /* uniformName = */ "iMouse",
//            /* value1 = */ positionY(),
//            /* value2 = */ positionX(),
//        )
//    }
//
//    fun updateIterations(iterations: ComposeFriendlyInt) {
//        setIntUniform(
//            /* uniformName = */ "iterations",
//            /* value = */ iterations()
//        )
//    }
//
//    fun updateVolSteps(volSteps: ComposeFriendlyInt) {
//        setIntUniform(
//            /* uniformName = */ "volsteps",
//            /* value = */ volSteps()
//        )
//    }
//
//    fun updateFormuparam(formuparam: ComposeFriendlyFloat) {
//        setFloatUniform(
//            /* uniformName = */ "formuparam",
//            /* value = */ formuparam()
//        )
//    }
//
//    fun updateStepsize(stepsize: ComposeFriendlyFloat) {
//        setFloatUniform(
//            /* uniformName = */ "stepsize",
//            /* value = */ stepsize()
//        )
//    }
//
//    fun updateZoom(zoom: ComposeFriendlyFloat) {
//        setFloatUniform(
//            /* uniformName = */ "zoom",
//            /* value = */ zoom()
//        )
//    }
//
//    fun updateTile(tile: ComposeFriendlyFloat) {
//        setFloatUniform(
//            /* uniformName = */ "tile",
//            /* value = */ tile()
//        )
//    }
//
//    fun updateSpeed(speed: ComposeFriendlyFloat) {
//        setFloatUniform(
//            /* uniformName = */ "speed",
//            /* value = */ speed()
//        )
//    }
//
//    fun updateBrightness(brightness: ComposeFriendlyFloat) {
//        setFloatUniform(
//            /* uniformName = */ "brightness",
//            /* value = */ brightness()
//        )
//    }
//
//    fun updateDarkmatter(darkmatter: ComposeFriendlyFloat) {
//        setFloatUniform(
//            /* uniformName = */ "darkmatter",
//            /* value = */ darkmatter()
//        )
//    }
//
//    fun updateDistFading(distFading: ComposeFriendlyFloat) {
//        setFloatUniform(
//            /* uniformName = */ "distfading",
//            /* value = */ distFading()
//        )
//    }
//
//    fun updateSaturation(saturation: ComposeFriendlyFloat) {
//        setFloatUniform(
//            /* uniformName = */ "saturation",
//            /* value = */ saturation()
//        )
//    }
//
//
//    private companion object {
//        val shader = """
//            // Star Nest by Pablo Roman Andrioli
//            uniform float2 iResolution;
//            uniform float iTime;
//            uniform float2 iMouse;
//
//            uniform float formuparam, stepsize, zoom, tile, speed, brightness, darkmatter, distfading, saturation;
//
//            const int iterations = 17;
//            const int volsteps = 20;
//
//            half4 main(float2 fragCoord) {
//                float2 uv = fragCoord / iResolution - 0.5;
//                uv.y *= iResolution.y / iResolution.x;
//                float3 dir = float3(uv * zoom, 1.0);
//                float time = iTime * speed + 0.25;
//
//                float a1 = 0.5 + iMouse.x / iResolution.x * 2.0;
//                float a2 = 0.8 + iMouse.y / iResolution.y * 2.0;
//                float2x2 rot1 = float2x2(cos(a1), sin(a1), -sin(a1), cos(a1));
//                float2x2 rot2 = float2x2(cos(a2), sin(a2), -sin(a2), cos(a2));
//                dir.xz *= rot1;
//                dir.xy *= rot2;
//                float3 from = float3(1.0, 0.5, 0.5);
//                from += float3(time * 2.0, time, -2.0);
//                from.xz *= rot1;
//                from.xy *= rot2;
//
//                float s = 0.1;
//                float fade = 1.0;
//                float3 v = float3(0.0);
//                for (int r = 0; r < volsteps; r++) {
//                    float3 p = from + s * dir * 0.5;
//                    p = abs(float3(tile) - mod(p, float3(tile * 2.0)));
//                    float pa, a = pa = 0.0;
//                    for (int i = 0; i < iterations; i++) {
//                    p = abs(p) / dot(p, p) - formuparam;
//                    a += abs(length(p) - pa);
//                    pa = length(p);
//                }
//                float dm = max(0.0, darkmatter - a * a * 0.001);
//                a *= a * a;
//                if (r > 6) fade *= 1.0 - dm;
//                v += fade;
//                v += float3(s, s * s, s * s * s * s) * a * brightness * fade;
//                fade *= distfading;
//                s += stepsize;
//            }
//            v = mix(float3(length(v)), v, saturation);
//            return half4(v * 0.01, 1.0);
//        }
//        """.trimIndent()
//    }
//}

import com.mikepenz.hypnoticcanvas.RuntimeEffect
import com.mikepenz.hypnoticcanvas.shaders.Shader

/**
 * CMP-compatible Interstellar Space shader using HypnoticCanvas.
 *
 * "Star Nest" by Pablo Roman Andrioli — converted from AGSL to SKSL.
 * Mutable properties are updated from the composable; [applyUniforms] pushes them each frame.
 */
class InterstellarSpaceHcShader : Shader {
    override val name = "InterstellarSpace"
    override val authorName = "Pablo Roman Andrioli"
    override val authorUrl = ""
    override val credit = "Star Nest"
    override val license = ""
    override val licenseUrl = ""
    override val speedModifier = 1f

    // Runtime-configurable uniforms
    var formuparam: Float = 0.53f
    var stepsize: Float = 0.1f
    var zoom: Float = 0.800f
    var tile: Float = 0.850f
    var speed: Float = 0.010f
    var brightness: Float = 0.0015f
    var darkmatter: Float = 0.300f
    var distfading: Float = 0.730f
    var saturation: Float = 0.850f
    var mouseX: Float = 0f
    var mouseY: Float = 0f

    override val sksl = SHADER

    fun updateUniforms(state: InterstellarSpaceState) {
        formuparam = state.formuparam
        stepsize = state.stepsize
        zoom = state.zoom
        tile = state.tile
        speed = state.speed
        brightness = state.brightness
        darkmatter = state.darkmatter
        distfading = state.distFading
        saturation = state.saturation
    }

    override fun applyUniforms(
        runtimeEffect: RuntimeEffect,
        time: Float,
        width: Float,
        height: Float
    ) {
        runtimeEffect.setFloatUniform("iResolution", width, height)
        runtimeEffect.setFloatUniform("iTime", time)
        runtimeEffect.setFloatUniform("iMouse", mouseX, mouseY)
        runtimeEffect.setFloatUniform("formuparam", formuparam)
        runtimeEffect.setFloatUniform("stepsize", stepsize)
        runtimeEffect.setFloatUniform("zoom", zoom)
        runtimeEffect.setFloatUniform("tile", tile)
        runtimeEffect.setFloatUniform("speed", speed)
        runtimeEffect.setFloatUniform("brightness", brightness)
        runtimeEffect.setFloatUniform("darkmatter", darkmatter)
        runtimeEffect.setFloatUniform("distfading", distfading)
        runtimeEffect.setFloatUniform("saturation", saturation)
    }

    companion object {
        // SKSL version of the original AGSL shader
        val SHADER = """
            // Star Nest by Pablo Roman Andrioli
            uniform float2 iResolution;
            uniform float iTime;
            uniform float2 iMouse;

            uniform float formuparam, stepsize, zoom, tile, speed, brightness, darkmatter, distfading, saturation;

            const int iterations = 17;
            const int volsteps = 20;

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
                    float pa = 0.0;
                    float a = 0.0;
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
