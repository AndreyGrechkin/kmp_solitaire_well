package screens

import base_viewModel.UiAction
import base_viewModel.UiEvent
import base_viewModel.UiState
import model.CardStack
import model.GameState
import model.SlotAddress

object WellContract {
    data class WellState(
        val isStockAnimating: Boolean = false,
        val stackWells: List<CardStack> = emptyList(),
        val gameState: GameState = GameState.EMPTY
    ) : UiState

    sealed interface WellEvent : UiEvent {
        data object NavigateToSettings : WellEvent
        data class ClickCard(val state: GameState) : WellEvent
        data object OnNewGame : WellEvent
    }

    sealed interface WellAction : UiAction {

    }
}