package setting

import base_viewModel.UiAction
import base_viewModel.UiEvent
import base_viewModel.UiState
import models.UserData

object SettingsContract {
    data class SettingsState(
        val userData: UserData
    ) : UiState

    sealed interface SettingsEvent : UiEvent {
        data object GoBack : SettingsEvent
    }
    sealed interface SettingsAction : UiAction {

    }
}