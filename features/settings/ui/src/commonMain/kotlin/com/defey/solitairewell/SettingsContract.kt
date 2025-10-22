package com.defey.solitairewell

import base_viewModel.UiAction
import base_viewModel.UiEvent
import base_viewModel.UiState
import managers.AppLanguage
import models.Deck

object SettingsContract {
    data class SettingsState(
        val deck: Deck = Deck.FIRST,
        val backCardIndex: Int = 0,
        val backgroundItemIndex: Int = 0,
        val currentLanguage: AppLanguage = AppLanguage.SYSTEM
    ) : UiState

    sealed interface SettingsEvent : UiEvent {
        data object GoBack : SettingsEvent
        data class SaveDeck(val deck: Deck) : SettingsEvent
        data class SaveBackCard(val index: Int) : SettingsEvent
        data class SaveBackgroundItem(val index: Int) : SettingsEvent
        data class SaveLanguage(val language: AppLanguage) : SettingsEvent
    }

    sealed interface SettingsAction : UiAction
}