import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import pro.jayeshseth.animations.convention.configureKotlinAndroid
import pro.jayeshseth.animations.convention.configureKotlinMultiplatform
import pro.jayeshseth.animations.convention.libs
import pro.jayeshseth.animations.convention.pathToResourcePrefix

/**
 * A Gradle convention plugin for configuring a Kotlin Multiplatform (KMP) library module.
 * This plugin applies the necessary plugins and configurations for a standard KMP library
 * that includes an Android target. It sets up Kotlin Multiplatform, Android library specifics,
 * serialization, and common dependencies.
 *
 * It applies the following plugins:
 * - `com.android.library`
 * - `org.jetbrains.kotlin.multiplatform`
 * - `org.jetbrains.kotlin.plugin.serialization`
 *
 * It also configures:
 * - Kotlin Multiplatform source sets and targets via `configureKotlinMultiplatform()`.
 * - Android-specific settings like compile SDK and build types via `configureKotlinAndroid()`.
 * - A unique resource prefix based on the project's path.
 * - Experimental KMP properties for Android resource support.
 * - Common dependencies for `commonMain` and `commonTest` source sets, such as lifecycle-compose,
 *   serialization, and kotlin-test.
 */
class KmpLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            configureKotlinMultiplatform()

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                resourcePrefix = this@with.pathToResourcePrefix()

                // Required to make debug build of app run in iOS simulator
                experimentalProperties["android.experimental.kmp.enableAndroidResources"] = "true"
            }

            dependencies {
                "commonMainImplementation"(libs.findLibrary("jetbrains-lifecycle-compose").get())
                "commonMainImplementation"(libs.findLibrary("kotlinx-serialization-json").get())
                "commonTestImplementation"(libs.findLibrary("kotlin-test").get())
            }
        }
    }
}