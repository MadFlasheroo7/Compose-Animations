plugins {
    alias(libs.plugins.animations.cmp.library)
    alias(libs.plugins.kotlinAtomicfu)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.compose.nav3)
            }
        }
    }
}

