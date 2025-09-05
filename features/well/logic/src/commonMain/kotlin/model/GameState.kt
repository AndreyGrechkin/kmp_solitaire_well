package model

import models.Card
import models.WellSlotAddress
import models.WellSlotType

data class GameState(
    val card: Card?,
    val address: WellSlotAddress,
    val state: CardState,
) {
    companion object {
        val EMPTY = GameState(
            card = null,
            address = WellSlotAddress(
                WellSlotType.STOCK
            ),
            state = CardState.DEFAULT
        )
    }
}
