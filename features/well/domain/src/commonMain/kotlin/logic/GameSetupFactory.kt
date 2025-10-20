package logic

import models.WellCardStack
import model.GameState
import model.MoveCardResult
import model.StockClickResult

interface GameSetupFactory {
    fun createNewGame(): List<WellCardStack>

    fun handleStockClick(
        currentStacks: List<WellCardStack>,
        currentDealCount: Int,
    ): StockClickResult

    fun handleMoveCard(
        currentStacks: List<WellCardStack>,
        oldGameState: GameState,
        currentGameState: GameState,
    ): MoveCardResult

    fun handleHint(currentStacks: List<WellCardStack>): List<GameState>
}