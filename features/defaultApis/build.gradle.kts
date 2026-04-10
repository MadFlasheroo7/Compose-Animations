plugins {
    alias(libs.plugins.animations.cmp.feature)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.components.uiToolingPreview)

                implementation(compose.components.resources)
                implementation(libs.haze)
                implementation(libs.hypnoticcanvas)
                implementation(libs.hypnoticcanvas.shaders)
            }
        }
    }
}