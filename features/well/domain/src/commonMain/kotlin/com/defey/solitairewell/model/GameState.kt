package com.defey.solitairewell.model

import com.defey.solitairewell.models.Card
import com.defey.solitairewell.models.WellSlotAddress
import com.defey.solitairewell.models.WellSlotType

data class GameState(
    val card: Card?,
    val address: WellSlotAddress,
    val state: CardState,
) {
    companion object {
        val EMPTY = GameState(
            card = null,
            address = WellSlotAddress(type = WellSlotType.STOCK),
            state = CardState.DEFAULT
        )
    }
}