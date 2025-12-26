plugins {
    alias(libs.plugins.animations.cmp.feature)
}

//dependencies {
//    implementation(projects.core.ui)
//    implementation(projects.core.model)
//    implementation(projects.core.navigation)
//    implementation(libs.physics.layout)
//    implementation(libs.haze)
//}

kotlin {
    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.compose.nav3)
                implementation(projects.core.navigation)
                implementation(libs.physics.layout)
                implementation(libs.haze)
//                implementation(libs.androidx.activity.compose)
            }
        }
        commonMain {
            dependencies {
                implementation(libs.compose.nav3)

                implementation(projects.core.model)
                implementation(projects.core.navigation)
                implementation(libs.physics.layout)
                implementation(libs.haze)
            }
        }
    }
}