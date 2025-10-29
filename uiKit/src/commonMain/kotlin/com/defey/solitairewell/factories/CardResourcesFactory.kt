package com.defey.solitairewell.factories

import com.defey.solitairewell.models.Card
import com.defey.solitairewell.models.Deck
import org.jetbrains.compose.resources.DrawableResource

interface CardResourcesFactory {
    val backCards: List<DrawableResource>
    val backGround: List<BackgroundItem>
    fun createCardResources(card: Card, deck: Deck): CardResource
    fun createBackCard(index: Int): DrawableResource
    fun createBackGround(index: Int): BackgroundItem
}