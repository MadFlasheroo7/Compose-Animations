package pro.jayeshseth.animations.convention

import org.gradle.api.Project
import java.util.Locale

/**
 * Converts a Gradle project's path into a valid Android package name.
 *
 * This function takes the project's path (e.g., `:feature:home`) and transforms it
 * into a package name by replacing colons with dots and converting it to lowercase.
 * It then prepends a base package name (`pro.jayeshseth`) to form the full package name.
 *
 * For example, a project with the path `:feature:home` will be converted to
 * `pro.jayeshseth.feature.home`.
 *
 * @return The generated package name as a [String].
 */
fun Project.pathToPackageName(): String {
    val relativePackageName = path
        .replace(':', '.')
        .lowercase()

    println("pathToPackageName: pro.jayeshseth$relativePackageName")
    return "pro.jayeshseth$relativePackageName"
}

/**
 * Converts a Gradle project path into a resource prefix.
 *
 * This is used to create unique resource prefixes for each module, preventing resource name collisions.
 * The transformation involves:
 * 1. Replacing colons (`:`) with underscores (`_`).
 * 2. Converting the entire string to lowercase.
 * 3. Removing the initial underscore (which comes from the root project's leading colon).
 * 4. Appending a final underscore to signify it's a prefix.
 *
 * For example, a project path `:feature:home` would be transformed into `feature_home_`.
 *
 * @return A string suitable for use as a resource file prefix.
 */
fun Project.pathToResourcePrefix(): String {
    return path
        .replace(':', '_')
        .lowercase()
        .drop(1) + "_"
}

/**
 * Converts a Gradle project path into a CamelCase framework name.
 *
 * This is used for naming iOS frameworks in a Kotlin Multiplatform project.
 * It takes the project path, splits it by common delimiters (`:`, `-`, `_`, ` `),
 * capitalizes the first letter of each part, and then joins them together
 * without any separators.
 *
 * For example, a project path like `:feature:my-awesome-feature` would be
 * converted to `FeatureMyAwesomeFeature`.
 *
 * @return The CamelCase representation of the project path.
 */
fun Project.pathToFrameworkName(): String {
    val parts = this.path.split(":", "-", "_", " ")
    return parts.joinToString("") { part ->
        part.replaceFirstChar {
            it.titlecase(Locale.ROOT)
        }
    }
}