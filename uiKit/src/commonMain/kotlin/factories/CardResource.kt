package factories

import org.jetbrains.compose.resources.DrawableResource

data class CardResource(
    val rank: String,
    val suit: DrawableResource,
    val image: DrawableResource
)