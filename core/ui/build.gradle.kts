plugins {
    alias(libs.plugins.animations.library)
}

android {
    namespace = "pro.jayeshseth.animations.core.ui"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.commonmodule)
    implementation(libs.rebugger)
    implementation(libs.compose.code.editor)

}