package model

import models.Card

data class GameState(
    val card: Card?,
    val address: SlotAddress,
    val state: CardState,
) {
    companion object {
        val EMPTY = GameState(
            card = null,
            address = SlotAddress(
                SlotType.STOCK
            ),
            state = CardState.DEFAULT
        )
    }
}
