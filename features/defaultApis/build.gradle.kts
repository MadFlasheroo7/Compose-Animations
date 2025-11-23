plugins {
    alias(libs.plugins.animations.library)
}

android {
    namespace = "pro.jayeshseth.animations.defaultApis"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.model)
    implementation(projects.core.navigation)
    implementation(libs.commonmodule)
    implementation(libs.haze)
    implementation(libs.hypnoticcanvas)
    implementation(libs.hypnoticcanvas.shaders)
}