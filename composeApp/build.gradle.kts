import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.animations.cmp.application)
}

kotlin {
    sourceSets {
        webMain {
            dependencies {
                implementation(npm("@js-joda/core", "3.2.0"))
                implementation(libs.kotlinx.browser)
            }
        }
        androidMain {
            dependencies {
                implementation(compose.preview)
                implementation("androidx.compose.foundation:foundation:1.11.0-alpha04")
                implementation(libs.androidx.activity.compose)
                implementation(fileTree("libs") { include("*.jar") })
                implementation(libs.rebugger)
            }
        }
        iosMain {
            dependencies {
                implementation(libs.rebugger)
            }
        }
        commonMain {
            dependencies {
                implementation(libs.glowingbutton)

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
                implementation(projects.features.masterCustomization)

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
                implementation(libs.rebugger)
            }
        }
    }
}

compose {
    desktop {
        application {
            mainClass = "pro.jayeshseth.animations.MainKt"

            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Rpm)
                modules("jdk.unsupported")
                packageName = "compose-animations"
                packageVersion = "1.0.0"
            }
        }
    }
}
