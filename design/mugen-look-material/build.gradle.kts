plugins {
    alias(libs.plugins.animations.cmp.library)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.design.mugen)
                // material3 needed for MaterialButtonRenderer ripple + MaterialTextRenderer
                implementation(compose.material3)
            }
        }
    }
}
