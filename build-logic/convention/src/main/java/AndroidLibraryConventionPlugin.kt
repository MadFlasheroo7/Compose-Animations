import com.android.build.gradle.LibraryExtension
import pro.jayeshseth.animations.convention.configureAndroidCompose
import pro.jayeshseth.animations.convention.configureKotlinAndroid
import pro.jayeshseth.animations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")


            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = libs.findVersion("target-sdk").get().toString().toInt()
                configureKotlinAndroid(this)
                configureAndroidCompose(this)
            }

            tasks.withType(JavaCompile::class.java).configureEach {
                targetCompatibility = libs.findVersion("jvm-target").get().toString()
                sourceCompatibility = libs.findVersion("jvm-target").get().toString()
            }

            dependencies {
                add("implementation", libs.findBundle("androidx").get())
                add("implementation", libs.findBundle("compose").get())
                add("implementation", libs.findBundle("navigation").get())
            }
        }
    }
}