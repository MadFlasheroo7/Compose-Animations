plugins {
    alias(libs.plugins.animations.cmp.library)
}

kotlin {
    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.compose.code.editor)
            }
        }
        commonMain {
            dependencies {
                implementation(compose.components.resources)
            }
        }
    }
}