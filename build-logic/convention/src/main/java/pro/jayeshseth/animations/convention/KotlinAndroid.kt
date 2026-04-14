package pro.jayeshseth.animations.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configures common Kotlin and Android settings for a Gradle project.
 *
 * This function applies a standard configuration to the given `CommonExtension` (which is used
 * by Android application and library plugins). The configurations include:
 *
 * - Setting `compileSdk` and `minSdk` from the version catalog (`libs`).
 * - Setting up the Java toolchain to version 17.
 * - Enabling the `buildConfig` build feature.
 * - Configuring Kotlin compiler options:
 *   - Opts into `RequiresOptIn` and `ExperimentalCoroutinesApi`.
 * - Configuring lint to not abort on errors.
 *
 * @param commonExtension The `CommonExtension` to configure, typically from an `android` block.
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        compileSdk = libs.findVersion("compile-sdk").get().toString().toInt()

        defaultConfig {
            minSdk = libs.findVersion("min-sdk").get().toString().toInt()
        }

        pluginManager.withPlugin("org.jetbrains.kotlin.android") {
            this@configureKotlinAndroid.extensions.configure<KotlinAndroidProjectExtension>("kotlin") {
                jvmToolchain(17)
            }
        }
        pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
            this@configureKotlinAndroid.extensions.configure<KotlinMultiplatformExtension>("kotlin") {
                jvmToolchain(17)
            }
        }

        buildFeatures {
            buildConfig = true
        }

        tasks.withType<KotlinCompile> {
            compilerOptions {
                freeCompilerArgs.addAll(
                    listOf(
                        "-opt-in=kotlin.RequiresOptIn",
                        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    )
                )
            }
        }

        lint {
            abortOnError = false
        }
    }
}