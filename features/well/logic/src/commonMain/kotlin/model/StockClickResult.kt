package model

data class StockClickResult(
    val updatedStacks: List<CardStack>,
    val newDealCount: Int
)
