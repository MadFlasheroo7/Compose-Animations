import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.animations.cmp.application)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.design.mugen)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
            }
        }
        desktopMain {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.androidx.activity.compose)
            }
        }
        webMain {
            dependencies {
                implementation(libs.kotlinx.browser)
            }
        }
    }
}

compose {
    desktop {
        application {
            mainClass = "pro.jayeshseth.mugen.playground.MainKt"

            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "mugen-playground"
                packageVersion = "1.0.0"
            }
        }
    }
}
