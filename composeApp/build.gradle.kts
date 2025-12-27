import org.jetbrains.compose.desktop.application.dsl.TargetFormat

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.animations.cmp.application)
}

kotlin {
    android {
        buildTypes {
            release {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            debug {
                applicationIdSuffix = ".debug"
                isDefault = true
                versionNameSuffix = libs.versions.version.nameBetaSuffix.get()
            }
        }
    }

    sourceSets {
        androidMain {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(fileTree("libs") { include("*.jar") })
            }
        }
        commonMain {
            dependencies {
                implementation(projects.core.ui)
                implementation(projects.core.navigation)
                implementation(projects.core.utils)
                implementation(projects.core.model)

                implementation(projects.features.defaultApis)
                implementation(projects.features.easterEggs)
                implementation(projects.features.itemPlacements)
                implementation(projects.features.navigation)
                implementation(projects.features.playground)
                implementation(projects.features.shaders)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.haze)
                implementation(libs.hypnoticcanvas)
                implementation(libs.hypnoticcanvas.shaders)
                implementation(libs.bundles.navigation)
            }
        }
        desktopMain {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }

    }
}

compose {
    desktop {
        application {
            mainClass = "pro.jayeshseth.animations.MainKt"

            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "pro.jayeshseth.animations"
                packageVersion = "1.0.0"
            }
        }
    }
}