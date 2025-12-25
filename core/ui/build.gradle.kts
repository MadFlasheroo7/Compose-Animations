plugins {
    alias(libs.plugins.animations.cmp.library)
}

//android {
//    namespace = "pro.jayeshseth.animations.core.ui"
//}

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
                implementation(libs.haze)
                implementation(libs.compose.unstyled)
                implementation(libs.hypnoticcanvas)
                implementation(libs.hypnoticcanvas.shaders)
            }
        }
    }
}

//dependencies {
//    implementation(projects.core.model)
//    implementation(libs.palette)
//    implementation(libs.compose.code.editor)
//    implementation(libs.compose.animation.graphics)
//
//}