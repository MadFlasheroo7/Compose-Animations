import java.util.Properties

plugins {
    alias(libs.plugins.animations.android.application.compose)
    alias(libs.plugins.baselineprofile)
}

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties().apply {
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { load(it) }
    }
}

fun signingValue(key: String, envKey: String): String? =
    localProperties.getProperty(key) ?: System.getenv(envKey)

android {
    signingConfigs {
        create("release") {
            val storeFilePath = signingValue("RELEASE_STORE_FILE", "STORE_FILE")
            if (storeFilePath != null) {
                storeFile = rootProject.file(storeFilePath)
            }
            storePassword = signingValue("RELEASE_STORE_PASSWORD", "STORE_PASSWORD")
            keyAlias = signingValue("RELEASE_KEY_ALIAS", "KEY_ALIAS")
            keyPassword = signingValue("RELEASE_KEY_PASSWORD", "KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("benchmark") {
            initWith(getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = false
            isMinifyEnabled = false
            isShrinkResources = false
            matchingFallbacks += listOf("release")
        }
        debug {
            applicationIdSuffix = ".debug"
            isDefault = true
            versionNameSuffix = libs.versions.version.nameBetaSuffix.get()
        }
    }
}

dependencies {
    implementation(projects.composeApp)
    implementation(libs.androidx.activity.compose)
    "baselineProfile"(project(":macrobenchmark"))
}

baselineProfile {
    mergeIntoMain = true
}