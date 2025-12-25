import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import pro.jayeshseth.animations.convention.libs

class CmpFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("animations.cmp.library")
            }

            dependencies {
                "commonMainImplementation"(project(":core:ui"))
                "commonMainImplementation"(project(":core:utils"))
                "commonMainImplementation"(project(":core:model"))
                "commonMainImplementation"(project(":core:navigation"))
                "commonMainImplementation"(libs.findLibrary("jetbrains-lifecycle-compose").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-runtime").get())
                "commonMainImplementation"(libs.findLibrary("compose-nav3").get())
                "commonMainImplementation"(libs.findLibrary("haze").get())

                "commonMainImplementation"(libs.findLibrary("jetbrains-savedstate").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-bundle").get())
                "debugImplementation"(libs.findLibrary("compose-ui-tooling").get())
            }
        }
    }
}