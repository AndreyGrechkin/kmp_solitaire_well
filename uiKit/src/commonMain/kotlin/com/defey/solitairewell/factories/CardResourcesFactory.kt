package com.defey.solitairewell.factories

import models.Card
import models.Deck
import org.jetbrains.compose.resources.DrawableResource

interface CardResourcesFactory {
    val backCards: List<DrawableResource>
    val backGround: List<BackgroundItem>
    fun createCardResources(card: Card, deck: Deck): CardResource
    fun createBackCard(index: Int): DrawableResource
    fun createBackGround(index: Int): BackgroundItem
}