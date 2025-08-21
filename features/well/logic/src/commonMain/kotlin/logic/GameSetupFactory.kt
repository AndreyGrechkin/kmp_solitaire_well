package logic

import model.CardStack
import model.GameState
import model.MoveCardResult
import model.StockClickResult

interface GameSetupFactory {
    fun createNewGame(): List<CardStack>
    fun handleStockClick(
        currentStacks: List<CardStack>,
        currentDealCount: Int,
    ): StockClickResult

    fun handleMoveCard(
        currentStacks: List<CardStack>,
        oldGameState: GameState,
        currentGameState: GameState,
    ): MoveCardResult
}