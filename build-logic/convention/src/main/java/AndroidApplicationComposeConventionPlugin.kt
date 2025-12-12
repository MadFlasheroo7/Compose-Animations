import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import pro.jayeshseth.animations.convention.configureAndroidCompose
import pro.jayeshseth.animations.convention.configureKotlinAndroid

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("animations.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureAndroidCompose(this)
                configureKotlinAndroid(this)
            }
        }
    }
}