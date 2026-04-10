plugins {
    alias(libs.plugins.animations.cmp.feature)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
            }
        }
    }
}