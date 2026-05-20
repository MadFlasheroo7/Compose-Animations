import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import pro.jayeshseth.animations.convention.libs

/**
 * A Gradle convention plugin for setting up a Compose Multiplatform "feature" module.
 *
 * This plugin applies common configurations and dependencies required for a feature module
 * in a Compose Multiplatform project. It simplifies the build script for individual feature
 * modules by encapsulating shared setup logic.
 *
 * It applies the following plugins:
 * - `animations.cmp.library`: A custom convention plugin for base library setup.
 * - `org.jetbrains.compose.hot-reload`: Enables hot-reload functionality for Compose.
 *
 * It adds the following dependencies:
 * - **Compose Bill of Materials (BOM)** for version alignment.
 * - Core project modules like `:core:ui`, `:core:utils`, `:core:model`, and `:core:navigation`.
 * - Essential Compose Multiplatform libraries (lifecycle, runtime, navigation, state saving).
 * - Android-specific Compose and Activity libraries.
 * - Debug-specific tooling like `compose-ui-tooling`.
 */
class CmpFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("animations.cmp.library")
                apply("org.jetbrains.compose.hot-reload")
            }

            dependencies {
                val bom = libs.findLibrary("compose-bom").get()
                "androidMainImplementation"(platform(bom))
                "commonMainImplementation"(project(":core:ui"))
                "commonMainImplementation"(project(":core:utils"))
                "commonMainImplementation"(project(":core:model"))
                "commonMainImplementation"(project(":core:navigation"))
                "commonMainImplementation"(libs.findLibrary("jetbrains-lifecycle-compose").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-runtime").get())
                "commonMainImplementation"(libs.findLibrary("compose-nav3").get())
                "commonMainImplementation"(libs.findLibrary("haze").get())
                "androidMainImplementation"(libs.findBundle("compose").get())
                "androidMainImplementation"(libs.findLibrary("androidx-activity-ktx").get())
                "androidMainImplementation"(libs.findLibrary("androidx-activity-compose").get())

                "commonMainImplementation"(libs.findLibrary("jetbrains-savedstate").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-bundle").get())
            }
        }
    }
}