import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import pro.jayeshseth.animations.convention.configureAndroidCompose
import pro.jayeshseth.animations.convention.configureKotlinAndroid

/**
 * A Gradle plugin that configures an Android application module for Jetpack Compose.
 *
 * This plugin applies the following conventions:
 * - Applies the `animations.android.application` plugin for base Android application setup.
 * - Applies the `org.jetbrains.kotlin.plugin.compose` plugin to enable the Jetpack Compose compiler.
 * - Configures Android-specific options for Compose using [configureAndroidCompose].
 * - Configures common Kotlin options for Android using [configureKotlinAndroid].
 */
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