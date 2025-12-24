@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

package pro.jayeshseth.animations.convention

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinHierarchyTemplate
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

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