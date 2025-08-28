package screens

import base_viewModel.UiAction
import base_viewModel.UiEvent
import base_viewModel.UiState
import model.CardStack
import model.GameState

object WellContract {
    data class WellState(
        val stackWells: List<CardStack> = emptyList(),
        val gameState: GameState = GameState.EMPTY,
        val gameMessage: String = "",
        val availableHint: Boolean = true,
        val availableBackMove: Boolean = false,
        val hintState: List<GameState> = emptyList(),
    ) : UiState

    sealed interface WellEvent : UiEvent {
        data object NavigateToSettings : WellEvent
        data class OnClickCard(val state: GameState) : WellEvent
        data object OnAnimationFinished : WellEvent
        data object OnNewGame : WellEvent
        data object OnBackMove : WellEvent
        data object OnHelp : WellEvent
    }

    sealed interface WellAction : UiAction
}