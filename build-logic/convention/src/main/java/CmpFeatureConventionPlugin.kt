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
                "debugImplementation"(libs.findLibrary("compose-ui-tooling").get())
            }
        }
    }
}