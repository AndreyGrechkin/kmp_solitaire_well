package model

import models.Card

data class GameState(
    val cards: Card?,
    val address: SlotAddress,
    val state: CardState,
) {
    companion object {
        val EMPTY = GameState(
            cards = null,
            address = SlotAddress(
                SlotType.STOCK
            ),
            state = CardState.DEFAULT
        )
    }
}
