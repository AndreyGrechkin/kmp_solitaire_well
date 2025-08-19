package screens

import base_viewModel.UiAction
import base_viewModel.UiEvent
import base_viewModel.UiState
import model.CardStack
import model.SlotAddress

object WellContract {
    data class WellState(
        val isStockAnimating: Boolean = false,
        val stackWells: List<CardStack> = emptyList(),
    ) : UiState

    sealed interface WellEvent : UiEvent {
        data object NavigateToSettings : WellEvent
        data class ClickCard(val address: SlotAddress) : WellEvent
        data object OnNewGame : WellEvent
    }

    sealed interface WellAction : UiAction {

    }
}