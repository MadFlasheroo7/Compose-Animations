plugins {
    alias(libs.plugins.animations.library)
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "pro.jayeshseth.animations.core.utils"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.preferences.core)
}