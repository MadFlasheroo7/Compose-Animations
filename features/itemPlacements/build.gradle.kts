plugins {
    alias(libs.plugins.animations.library)
}

android {
    namespace = "pro.jayeshseth.animations.itemPlacements"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation(projects.core.navigation)
    implementation(libs.commonmodule)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.hypnoticcanvas)
    implementation(libs.hypnoticcanvas.shaders)
}