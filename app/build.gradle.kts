@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.animations.application)
}

android {
    namespace = libs.versions.namespace.get()
    compileSdk = libs.versions.compile.sdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        minSdk = libs.versions.min.sdk.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            isDefault = true
            versionNameSuffix = libs.versions.version.nameBetaSuffix.get()
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.utils)
    implementation(projects.core.model)
    implementation(projects.core.navigation)
    implementation(projects.features.defaultApis)
    implementation(projects.features.playground)
    implementation(projects.features.itemPlacements)
    implementation(projects.features.shaders)
    implementation(projects.features.easterEggs)
    implementation(projects.features.navigation)
    implementation(libs.hypnoticcanvas)
    implementation(libs.hypnoticcanvas.shaders)

    implementation(libs.commonmodule)
    implementation(libs.haze)
    implementation(libs.cascade)
    implementation(libs.cascade.compose)
    implementation(libs.core.ktx)
    implementation(libs.rebugger)
    implementation(libs.compose.code.editor)
    implementation(libs.kotlin.stdlib)
    implementation(libs.lifecycle.viewmodel.navigation3)

    implementation(libs.physics.layout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}