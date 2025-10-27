plugins {
    `kotlin-dsl`
}

group = "pro.jayeshseth.animation.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibraryCompose") {
            id = "animations.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "animations.android.application"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
    }
}