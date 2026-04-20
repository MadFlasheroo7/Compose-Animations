plugins {
    alias(libs.plugins.animations.cmp.library)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.ui)
                implementation(libs.compose.nav3)
                implementation(libs.material3.adaptive)
                implementation("org.jetbrains.kotlinx:atomicfu:${libs.versions.kotlin.atomic.get()}")
            }
        }
    }
}

