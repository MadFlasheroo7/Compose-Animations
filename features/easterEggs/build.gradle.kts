plugins {
    alias(libs.plugins.animations.cmp.feature)
}

kotlin {
    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.compose.nav3)
                implementation(projects.core.navigation)
                implementation(libs.physics.layout)
                implementation(libs.haze)
            }
        }
        commonMain {
            dependencies {
            }
        }
    }
}