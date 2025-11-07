import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import pro.jayeshseth.animations.convention.configureAndroidCompose
import pro.jayeshseth.animations.convention.configureKotlinAndroid
import pro.jayeshseth.animations.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            extensions.configure<BaseAppModuleExtension> {
                defaultConfig.targetSdk = libs.findVersion("target-sdk").get().toString().toInt()
                configureAndroidCompose(this)
                configureKotlinAndroid(this)
            }

            dependencies {
                add("implementation", libs.findLibrary("kotlinx-serialization-core").get())
                add("implementation", libs.findLibrary("kotlinx-serialization-json").get())
                add("implementation", libs.findBundle("androidx").get())
                add("implementation", libs.findBundle("compose").get())
                add("implementation", libs.findBundle("navigation").get())
            }
        }
    }
}