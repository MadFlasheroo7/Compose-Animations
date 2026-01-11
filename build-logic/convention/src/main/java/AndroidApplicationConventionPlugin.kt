import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import pro.jayeshseth.animations.convention.configureKotlinAndroid
import pro.jayeshseth.animations.convention.libs

/**
 * A Gradle convention plugin for configuring an Android Application module.
 *
 * This plugin applies the `com.android.application` plugin and sets up common
 * configurations for an Android application, including:
 * - Setting the `namespace`, `applicationId`, `targetSdk`, `versionCode`, and `versionName`
 *   from the version catalog (`libs.versions.toml`).
 * - Configuring resource packaging to exclude common license files.
 * - Disabling minification for the `release` build type.
 * - Applying common Kotlin Android configurations via the `configureKotlinAndroid` extension function.
 */
class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<ApplicationExtension>() {
                namespace = libs.findVersion("namespace").get().toString()

                defaultConfig {
                    applicationId = libs.findVersion("applicationId").get().toString()
                    targetSdk = libs.findVersion("target-sdk").get().toString().toInt()
                    versionCode = libs.findVersion("version-code").get().toString().toInt()
                    versionName = libs.findVersion("version-name").get().toString()
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                    }
                }
                configureKotlinAndroid(this)
            }
        }
    }
}