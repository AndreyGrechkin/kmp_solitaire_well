import base_viewModel.UiAction
import base_viewModel.UiEvent
import base_viewModel.UiState
import models.Deck

object SettingsContract {
    data class SettingsState(
        val deck: Deck = Deck.FIRST,
        val backCardIndex: Int = 0
    ) : UiState

    sealed interface SettingsEvent : UiEvent {
        data object GoBack : SettingsEvent
        data class SaveDeck(val deck: Deck) : SettingsEvent
        data class SaveBackCard(val index: Int) : SettingsEvent
    }
    sealed interface SettingsAction : UiAction {

    }
}