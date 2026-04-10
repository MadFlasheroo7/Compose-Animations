
plugins {
    alias(libs.plugins.animations.cmp.library)
}

kotlin {
    sourceSets {
        wasmJsMain {
            dependencies {
//                implementation(libs.rebugger)
            }
        }
        commonMain {
            dependencies {
//                implementation(libs.rebugger)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(projects.core.model)
                implementation(projects.core.utils)
//                implementation(libs.compose.material3.material3window)
                implementation(libs.material3.adaptive)
                implementation(libs.haze)
                implementation(libs.glowingbutton)
                implementation(libs.compose.unstyled)
                implementation(libs.hypnoticcanvas)
                implementation(libs.hypnoticcanvas.shaders)
            }
        }
    }
}