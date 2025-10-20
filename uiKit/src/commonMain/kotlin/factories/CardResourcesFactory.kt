package factories

import models.Card
import factories.CardResource
import models.Deck
import org.jetbrains.compose.resources.DrawableResource

interface CardResourcesFactory {
    val backCards: List<DrawableResource>
    fun createCardResources(card: Card, deck: Deck): CardResource
    fun createBackCard(index: Int): DrawableResource
}