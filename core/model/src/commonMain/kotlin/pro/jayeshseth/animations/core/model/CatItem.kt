package pro.jayeshseth.animations.core.model

/**
 * Represents a single cat item in the list.
 *
 * This data class holds the information needed to display a cat, including its unique identifier,
 * name, and a URL for its image.
 *
 * @property id The unique identifier for the cat item.
 * @property catName The name of the cat.
 * @property catImage The URL string for the cat's image. Defaults to a generic cat image URL.
 */
data class CatItem(
    val id: Int,
    val catName: String,
    val catImage: String = "https://cataas.com/cat",
)