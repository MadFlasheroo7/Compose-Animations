package pro.jayeshseth.animations.util

data class CatItem(
    val id: Int,
    val catName: String,
    val catImage: String = "https://source.unsplash.com/random/?cat/$id",
)