plugins {
    alias(libs.plugins.animations.cmp.feature)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.coil.kt)
                implementation(libs.coil.kt.compose)
            }
        }
    }
}