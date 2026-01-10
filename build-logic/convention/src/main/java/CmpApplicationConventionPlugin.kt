import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import pro.jayeshseth.animations.convention.applyHierarchyTemplate
import pro.jayeshseth.animations.convention.configureAndroidTarget
import pro.jayeshseth.animations.convention.configureDesktopTarget
import pro.jayeshseth.animations.convention.configureIosTargets
import pro.jayeshseth.animations.convention.configureWebTarget
import pro.jayeshseth.animations.convention.libs

/**
 * A Gradle convention plugin for configuring a Compose Multiplatform application module.
 *
 * This plugin applies the necessary plugins and configurations for a typical Compose Multiplatform
 * application targeting Android, iOS, Desktop, and Web.
 *
 * It applies the following plugins:
 * - `animations.android.application.compose`: A custom convention plugin for Android application specific setup with Compose.
 * - `org.jetbrains.kotlin.multiplatform`: For Kotlin Multiplatform support.
 * - `org.jetbrains.compose`: For JetBrains Compose Multiplatform.
 * - `org.jetbrains.kotlin.plugin.compose`: The Kotlin compiler plugin for Compose.
 * - `org.jetbrains.kotlin.plugin.serialization`: For Kotlin serialization.
 * - `org.jetbrains.compose.hot-reload`: Enables hot reload for Compose development.
 *
 * It also configures the following targets:
 * - Android Application
 * - iOS (arm64, x64, simulatorArm64)
 * - Desktop (JVM)
 * - Web (JS, WASM)
 *
 * Additionally, it sets up a standard source set hierarchy and adds the `compose-ui-tooling`
 * dependency for debug builds to support Compose previews and inspection.
 */
class CmpApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("animations.android.application.compose")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("org.jetbrains.compose.hot-reload")
            }

            configureAndroidTarget()
            configureIosTargets()
            configureDesktopTarget()
            configureWebTarget()

            extensions.configure<KotlinMultiplatformExtension> {
                applyHierarchyTemplate()
            }

            dependencies {
                "debugImplementation"(libs.findLibrary("compose-ui-tooling").get())
            }
        }
    }
}