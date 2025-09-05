package model

import models.WellCardStack

data class StockClickResult(
    val updatedStacks: List<WellCardStack>,
    val newDealCount: Int
)
