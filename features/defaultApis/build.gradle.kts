plugins {
    alias(libs.plugins.animations.cmp.feature)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.haze)
                implementation(libs.hypnoticcanvas)
                implementation(libs.hypnoticcanvas.shaders)
            }
        }
    }
}