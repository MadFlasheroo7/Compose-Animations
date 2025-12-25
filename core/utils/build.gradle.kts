plugins {
    alias(libs.plugins.animations.cmp.library)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(libs.androidx.datastore)
                api(libs.androidx.datastore.preferences)
            }
        }
    }
}