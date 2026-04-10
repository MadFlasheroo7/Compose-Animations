@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

package pro.jayeshseth.animations.convention

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinHierarchyTemplate
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

/**
 * Defines a custom source set hierarchy for Kotlin Multiplatform projects.
 * This template organizes source sets into logical groups, enabling code sharing
 * across different combinations of targets.
 *
 * It applies to both `main` and `test` source set trees.
 *
 * ### Hierarchy Structure:
 *
 * - **`common`**: The root source set, shared by all targets.
 *   - **`mobileAndDesktop`**: Shared by Android, JVM, and all iOS targets.
 *     - **`ios`**: Shared by all iOS targets (e.g., `iosX64`, `iosArm64`, `iosSimulatorArm64`).
 *   - **`web`**: Shared by JavaScript (`js`) and WebAssembly (`wasmJs`) targets.
 *   - **`mobile`**: Shared by Android and all iOS targets.
 *     - **`ios`**: (Redundant but harmless) Shared by all iOS targets.
 *   - **`jvmCommon`**: Shared by Android and JVM targets.
 *   - **`native`**: Shared by all native targets (e.g., iOS, macOS, Linux).
 *     - **`apple`**: Shared by all Apple targets (iOS, macOS, tvOS, watchOS).
 *       - **`ios`**: Shared by all iOS targets.
 *       - **`macos`**: Shared by all macOS targets.
 *
 * This structure is applied to a `KotlinMultiplatformExtension` using the
 * `applyHierarchyTemplate()` extension function.
 *
 * @see applyHierarchyTemplate
 */
private val hierarchyTemplate = KotlinHierarchyTemplate {
    withSourceSetTree(
        KotlinSourceSetTree.main,
        KotlinSourceSetTree.test,
    )

    common {
        withCompilations { true }

        group("mobileAndDesktop") {
            withAndroidTarget()
            withJvm()
            group("ios") {
                withIos()
            }
        }

        group("web") {
            withJs()
            withWasmJs()
        }

        group("mobile") {
            withAndroidTarget()
            group("ios") {
                withIos()
            }
        }

        group("jvmCommon") {
            withAndroidTarget()
            withJvm()
        }

        group("native") {
            withNative()

            group("apple") {
                withApple()

                group("ios") {
                    withIos()
                }

                group("macos") {
                    withMacos()
                }
            }
        }
    }
}

fun KotlinMultiplatformExtension.applyHierarchyTemplate() {
    applyHierarchyTemplate(hierarchyTemplate)
}