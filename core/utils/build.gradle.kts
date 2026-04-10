plugins {
    alias(libs.plugins.animations.cmp.library)
}

kotlin {
    sourceSets {
        val nonWebMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                api(libs.androidx.datastore)
                api(libs.androidx.datastore.preferences)
            }
        }

        androidMain {
            dependsOn(nonWebMain)
        }

        iosMain {
            dependsOn(nonWebMain)
        }

        desktopMain {
            dependsOn(nonWebMain)
        }

        commonMain {
            dependencies {
                implementation(projects.core.model)
            }
        }

        webMain {
            dependencies {
                implementation(libs.kotlinx.browser)
            }
        }
    }
}