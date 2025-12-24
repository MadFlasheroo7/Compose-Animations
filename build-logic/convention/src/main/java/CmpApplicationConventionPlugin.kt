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

class CmpApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("animations.android.application.compose")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
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