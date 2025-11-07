plugins {
    alias(libs.plugins.animations.library)
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "pro.jayeshseth.animations.core.utils"
}