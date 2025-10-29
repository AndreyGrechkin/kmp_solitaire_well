package com.defey.solitairewell.model

import com.defey.solitairewell.models.WellCardStack

data class StockClickResult(
    val updatedStacks: List<WellCardStack>,
    val newDealCount: Int
)
