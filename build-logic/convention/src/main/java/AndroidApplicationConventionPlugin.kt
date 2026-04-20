import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import pro.jayeshseth.animations.convention.configureKotlinAndroid
import pro.jayeshseth.animations.convention.libs

/**
 * A Gradle convention plugin for configuring an Android Application module.
 */
class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<ApplicationExtension> {
                namespace = libs.findVersion("namespace").get().toString() + ".app"

                defaultConfig.applicationId = libs.findVersion("applicationId").get().toString()
                defaultConfig.targetSdk = libs.findVersion("target-sdk").get().toString().toInt()
                defaultConfig.versionCode = libs.findVersion("version-code").get().toString().toInt()
                defaultConfig.versionName = libs.findVersion("version-name").get().toString()

                packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

                buildTypes.getByName("release") {
                    isMinifyEnabled = false
                }

                configureKotlinAndroid(this)
            }
        }
    }
}
