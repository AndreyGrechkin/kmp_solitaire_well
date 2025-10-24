package com.defey.solitairewell

import base_viewModel.UiAction
import base_viewModel.UiEvent
import base_viewModel.UiState
import com.defey.solitairewell.model.GameState
import models.Deck
import models.GameFinishStatus
import models.WellCardStack

object WellContract {
    data class WellState(
        val stackWells: List<WellCardStack> = emptyList(),
        val gameState: GameState = GameState.Companion.EMPTY,
        val gameStep: Int = 0,
        val availableHint: Boolean = true,
        val availableBackMove: Boolean = false,
        val hintState: List<GameState> = emptyList(),
        val deck: Deck = Deck.FIRST,
        val backCardIndex: Int = 0,
        val backgroundItemIndex: Int = 0,
    ) : UiState

    sealed interface WellEvent : UiEvent {
        data class OnClickCard(val state: GameState) : WellEvent
        data class OnMenu(val menu: WellMenu) : WellEvent
        data object OnAnimationFinished : WellEvent
        data object OnLoadGame : WellEvent
    }

    sealed interface WellAction : UiAction {
        data object ShowRenewalDialog : WellAction
        data object ShowRulesDialog : WellAction
        data class ShowWinDialog(val status: GameFinishStatus) : WellAction

    }
}