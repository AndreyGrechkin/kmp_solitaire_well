package logic

import models.Card
import models.CardResource

interface CardResourcesFactory {

    fun createCardResources(card: Card): CardResource
}