plugins {
    alias(libs.plugins.animations.cmp.library)
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                // api = consumers of mugen-look-haze automatically get mugen-core on the classpath
                api(projects.design.mugen)
                implementation(libs.haze)
                implementation(libs.glowingbutton)
                implementation(libs.hypnoticcanvas)
                implementation(libs.hypnoticcanvas.shaders)
            }
        }
    }
}
