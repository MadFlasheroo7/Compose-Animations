@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
        maven {
            url = uri("https://androidx.dev/snapshots/builds/13617490/artifacts/repository")
        }
        maven { url = uri("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://androidx.dev/snapshots/builds/13617490/artifacts/repository")
        }
        maven { url = uri("https://jitpack.io") }
    }
}
rootProject.name = "Animations"
include(":composeApp")
// Core Modules
include(":core:ui")
include(":core:utils")
include(":core:model")
include(":core:navigation")

// Features/Animations Modules
include(":features:defaultApis")
include(":features:playground")
include(":features:itemPlacements")
include(":features:shaders")
include(":features:easterEggs")
include(":features:navigation")
