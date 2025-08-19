package model

import models.Card

data class CardStack(
    val cards: List<Card>,
    val address: SlotAddress,
) {
    val topCard: Card? get() = cards.lastOrNull()

    companion object {
        val emptyStock =
            CardStack(cards = emptyList(), address = SlotAddress(type = SlotType.STOCK))
    }
}
