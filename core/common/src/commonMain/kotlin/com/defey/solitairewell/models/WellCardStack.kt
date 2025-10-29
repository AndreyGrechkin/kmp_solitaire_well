package com.defey.solitairewell.models

data class WellCardStack(
    val cards: List<Card>,
    val address: WellSlotAddress,
) {
    val topCard: Card? get() = cards.lastOrNull()

    companion object Companion {
        val emptyStock =
            WellCardStack(cards = emptyList(), address = WellSlotAddress(type = WellSlotType.STOCK))
    }
}
