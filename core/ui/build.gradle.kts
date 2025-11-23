plugins {
    alias(libs.plugins.animations.library)
}

android {
    namespace = "pro.jayeshseth.animations.core.ui"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.commonmodule)
    implementation(libs.haze)
    implementation(libs.rebugger)
    implementation(libs.compose.code.editor)
    implementation(libs.compose.animation.graphics)

    implementation(libs.hypnoticcanvas)
    implementation(libs.hypnoticcanvas.shaders)
}