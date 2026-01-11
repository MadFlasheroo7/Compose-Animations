plugins {
    alias(libs.plugins.animations.cmp.feature)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.components.uiToolingPreview) // TODO move to convention plugin
                implementation(compose.components.resources)
            }
        }
    }
}