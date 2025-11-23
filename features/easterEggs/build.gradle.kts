plugins {
    alias(libs.plugins.animations.library)
}

android {
    namespace = "pro.jayeshseth.animations.easterEggs"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.model)
    implementation(projects.core.navigation)
    implementation(libs.physics.layout)
    implementation(libs.haze)
}