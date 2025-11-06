plugins {
    alias(libs.plugins.animations.library)
}

android {
    namespace = "pro.jayeshseth.animations.navigation"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation(projects.core.navigation)
    implementation(libs.lottie)
    implementation(libs.commonmodule)
}