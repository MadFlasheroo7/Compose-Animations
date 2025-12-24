import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import pro.jayeshseth.animations.convention.libs

class CmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("org.jetbrains.compose")
                apply("animations.kmp.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            dependencies {
                "androidMainImplementation"(libs.findLibrary("core-ktx").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-ui").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-foundation").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material3").get())
                "commonMainImplementation"(
                    libs.findLibrary("jetbrains-compose-material-icons-core").get()
                )
                "commonMainImplementation"(
                    libs.findLibrary("jetbrains-compose-material-icons-extended").get()
                )

                "debugImplementation"(libs.findLibrary("compose-ui-tooling").get())
            }
        }
    }
}