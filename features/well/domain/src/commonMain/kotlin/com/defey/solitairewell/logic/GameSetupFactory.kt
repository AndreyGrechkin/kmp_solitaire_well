package com.defey.solitairewell.logic

import models.WellCardStack
import com.defey.solitairewell.model.GameState
import com.defey.solitairewell.model.MoveCardResult
import com.defey.solitairewell.model.StockClickResult

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