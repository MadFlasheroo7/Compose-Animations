plugins {
    alias(libs.plugins.animations.cmp.library)
    alias(libs.plugins.kotlinAtomicfu)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.ui)
                implementation(libs.compose.nav3)
                implementation(libs.material3.adaptive)
            }
        }
    }
}

