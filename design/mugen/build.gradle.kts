plugins {
    alias(libs.plugins.animations.cmp.library)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                // material3 only for PlainButtonRenderer ripple + PlainTextRenderer Text
                implementation(compose.material3)
            }
        }
    }
}
