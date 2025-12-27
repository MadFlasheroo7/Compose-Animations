import org.jetbrains.compose.desktop.application.dsl.TargetFormat

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.animations.cmp.application)
}

kotlin {
    sourceSets {
        androidMain {
            dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
                implementation(fileTree("libs") { include("*.jar") })
            }
        }
        commonMain {
            dependencies {
                implementation(projects.core.ui)
                implementation(projects.core.navigation)
                implementation(projects.core.utils)
                implementation(projects.core.model)

                implementation(projects.features.defaultApis)
                implementation(projects.features.easterEggs)
                implementation(projects.features.itemPlacements)
                implementation(projects.features.navigation)
                implementation(projects.features.playground)
                implementation(projects.features.shaders)
//                implementation(projects.feature.auth.presentation)
//
//                implementation(projects.feature.chat.data)
//                implementation(projects.feature.chat.database)
//                implementation(projects.feature.chat.domain)
//                implementation(projects.feature.chat.presentation)


                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.haze)
                implementation(libs.hypnoticcanvas)
                implementation(libs.hypnoticcanvas.shaders)
                implementation(libs.bundles.navigation)
            }
        }
        desktopMain {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }

    }
//    android {
////    namespace = libs.versions.namespace.get()
////    compileSdk = libs.versions.compile.sdk.get().toInt()
//
//        defaultConfig {
////        applicationId = libs.versions.applicationId.get()
////        minSdk = libs.versions.min.sdk.get().toInt()
//            versionCode = libs.versions.version.code.get().toInt()
//            versionName = libs.versions.version.name.get()
//
//            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//            vectorDrawables {
//                useSupportLibrary = true
//            }
//        }
//
//        buildTypes {
//            release {
//                isMinifyEnabled = true
//                proguardFiles(
//                    getDefaultProguardFile("proguard-android-optimize.txt"),
//                    "proguard-rules.pro"
//                )
//            }
//            debug {
//                applicationIdSuffix = ".debug"
//                isDefault = true
//                versionNameSuffix = libs.versions.version.nameBetaSuffix.get()
//            }
//        }
//        packaging {
//            resources {
//                excludes += "/META-INF/{AL2.0,LGPL2.1}"
//            }
//        }
//    }
}

compose {
    desktop {
        application {
            mainClass = "pro.jayeshseth.animations.MainKt"

            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "pro.jayeshseth.animations"
                packageVersion = "1.0.0"
            }
        }
    }
}

//dependencies {
//    implementation(projects.core.ui)
//    implementation(projects.core.utils)
//    implementation(projects.core.model)
//    implementation(projects.core.navigation)
//    implementation(projects.features.defaultApis)
////    implementation(projects.features.playground)
////    implementation(projects.features.itemPlacements)
////    implementation(projects.features.shaders)
////    implementation(projects.features.easterEggs)
////    implementation(projects.features.navigation)
//    implementation(libs.hypnoticcanvas)
//    implementation(libs.hypnoticcanvas.shaders)
//
//    implementation(libs.haze)
//    implementation(libs.palette)
//    implementation(libs.cascade)
//    implementation(libs.cascade.compose)
//    implementation(libs.core.ktx)
//    implementation(libs.rebugger)
//    implementation(libs.androidx.datastore.preferences)
////    implementation(libs.androidx.datastore.preferences.core)
//    implementation(libs.compose.code.editor)
//    implementation(libs.accompanist.permissions)
//    implementation(libs.kotlin.stdlib)
//    implementation(libs.lifecycle.viewmodel.navigation3)
//
//    implementation(libs.physics.layout)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.test.ext.junit)
//    androidTestImplementation(libs.espresso.core)
//
//    implementation(fileTree("libs") { include("*.jar") })
//}