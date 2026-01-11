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

        // iOS
        iosMain {
            dependsOn(nonWebMain)
        }

        // Desktop
        desktopMain {
            dependsOn(nonWebMain)
        }

        commonMain {
            dependencies {
            }
        }
    }
}