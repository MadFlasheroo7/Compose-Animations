package pro.jayeshseth.animations.ui.screens

//import org.intellij.lang.annotations.Language

//@Language("GLSL") // SKSL is compatible with GLSL tooling
val JELLY_SKSL = """
    uniform float2 resolution;
    uniform float progress;
    uniform shader content;  // Input content provided by the layer
    
    half4 main(float2 coord) {
        float2 uv = coord / resolution;
        float2 center = float2(0.5, 0.5);
        
        // Calculate distance for the bulge effect
        float dist = distance(uv, center);
        float2 dir = uv - center;
        
        // The Distortion Math
        float2 distortedUV = uv - dir * (progress * 0.3 * (1.0 - dist));
        
        // Boundary check to avoid ugly streaking at edges
        if (distortedUV.x < 0.0 || distortedUV.x > 1.0 || distortedUV.y < 0.0 || distortedUV.y > 1.0) {
            return half4(0.0, 0.0, 0.0, 0.0);
        }
        
        // Sample the input content at the new distorted coordinate
        // 'content' is automatically provided by saveLayer
        return content.eval(distortedUV * resolution);
    }
"""