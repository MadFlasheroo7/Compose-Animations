import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "pro.jayeshseth.animation.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kmpLibrary") {
            id = "animations.kmp.library"
            implementationClass = "KmpLibraryConventionPlugin"
        }
        register("cmpLibraryCompose") {
            id = "animations.cmp.library"
            implementationClass = "CmpLibraryConventionPlugin"
        }

        register("androidApplication") {
            id = "animations.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "animations.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("cmpApplication") {
            id = "animations.cmp.application"
            implementationClass = "CmpApplicationConventionPlugin"
        }

        register("cmpFeature") {
            id = "animations.cmp.feature"
            implementationClass = "CmpFeatureConventionPlugin"
        }
    }
}