import org.gradle.api.JavaVersion

plugins {
    alias(libs.plugins.androidTest)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "pro.jayeshseth.animations.benchmark"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] =
            "DEBUGGABLE,EMULATOR,LOW-BATTERY"
    }

    buildTypes {
        create("benchmark") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    targetProjectPath = ":composeApp"
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
    implementation(libs.androidx.benchmark.macro.junit4)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.test.ext.junit)
}

androidComponents {
    beforeVariants(selector().all()) {
        it.enable = it.buildType == "benchmark"
    }
}

// Ensure the target app is installed before running benchmarks
afterEvaluate {
    tasks.matching { it.name.contains("connectedBenchmarkAndroidTest") }.configureEach {
        dependsOn(":composeApp:installBenchmark")
    }
}
