package pro.jayeshseth.animations.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Configures the iOS targets for a Kotlin Multiplatform project.
 *
 * This extension function on [Project] accesses the [KotlinMultiplatformExtension]
 * and sets up the following iOS targets:
 * - `iosX64()` for x86_64 simulators.
 * - `iosArm64()` for ARM64-based iOS devices.
 * - `iosSimulatorArm64()` for ARM64-based simulators (Apple Silicon Macs).
 *
 * For each of these targets, it configures the output framework with a
 * base name of "ComposeApp" and sets it to be a static framework (`isStatic = true`).
 * This is a common setup for integrating a KMP shared module into an Xcode project.
 */
internal fun Project.configureIosTargets() {
    extensions.configure<KotlinMultiplatformExtension> {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "ComposeApp"
                isStatic = true
            }
        }
    }
}