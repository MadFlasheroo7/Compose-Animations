import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import pro.jayeshseth.animations.convention.configureKotlinMultiplatform
import pro.jayeshseth.animations.convention.libs
import pro.jayeshseth.animations.convention.pathToPackageName

/**
 * A Gradle convention plugin for configuring a Kotlin Multiplatform (KMP) library module.
 *
 * Uses the AGP 9.0 `com.android.kotlin.multiplatform.library` plugin which replaces
 * the old `com.android.library` + `org.jetbrains.kotlin.multiplatform` combination.
 */
class KmpLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.kotlin.multiplatform.library")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                targets.withType<KotlinMultiplatformAndroidLibraryTarget>().configureEach {
                    namespace = this@with.pathToPackageName()
                    compileSdk = libs.findVersion("compile-sdk").get().toString().toInt()
                    minSdk = libs.findVersion("min-sdk").get().toString().toInt()

                    androidResources.enable = true

                    lint {
                        abortOnError = false
                    }
                }
            }

            configureKotlinMultiplatform()

            dependencies {
                "commonMainImplementation"(libs.findLibrary("jetbrains-lifecycle-compose").get())
                "commonMainImplementation"(libs.findLibrary("kotlinx-serialization-json").get())
                "commonTestImplementation"(libs.findLibrary("kotlin-test").get())
            }
        }
    }
}
