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
//                implementation(libs.compose.material3.material3window)
                implementation(libs.material3.adaptive)
                implementation(libs.haze)
                implementation(libs.compose.unstyled)
                implementation(libs.hypnoticcanvas)
                implementation(libs.hypnoticcanvas.shaders)
            }
        }
    }
}