import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import pro.jayeshseth.animations.convention.applyHierarchyTemplate
import pro.jayeshseth.animations.convention.configureDesktopTarget
import pro.jayeshseth.animations.convention.configureIosTargets
import pro.jayeshseth.animations.convention.configureWebTarget
import pro.jayeshseth.animations.convention.libs

/**
 * A Gradle convention plugin for configuring a Compose Multiplatform shared module.
 *
 * With AGP 9.0, `com.android.application` + `org.jetbrains.kotlin.multiplatform` cannot
 * coexist. This plugin now configures the module as a KMP library using
 * `com.android.kotlin.multiplatform.library`. The Android application entry point
 * lives in the separate `:androidApp` module.
 */
class CmpApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.kotlin.multiplatform.library")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("org.jetbrains.compose.hot-reload")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                targets.withType<KotlinMultiplatformAndroidLibraryTarget>().configureEach {
                    namespace = libs.findVersion("namespace").get().toString()
                    compileSdk = libs.findVersion("compile-sdk").get().toString().toInt()
                    minSdk = libs.findVersion("min-sdk").get().toString().toInt()

                    androidResources.enable = true
                }

                jvmToolchain(17)
                applyHierarchyTemplate()
            }

            configureIosTargets()
            configureDesktopTarget()
            configureWebTarget()

        }
    }
}
