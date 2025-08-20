package logic

import model.CardStack
import model.StockClickResult

interface GameSetupFactory {
    fun createNewGame(): List<CardStack>
    fun handleStockClick(
        currentStacks: List<CardStack>,
        currentDealCount: Int
    ): StockClickResult
}