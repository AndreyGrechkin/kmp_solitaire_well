package com.defey.solitairewell

import com.defey.solitairewell.base_viewModel.UiAction
import com.defey.solitairewell.base_viewModel.UiEvent
import com.defey.solitairewell.base_viewModel.UiState
import com.defey.solitairewell.managers.AppLanguage
import com.defey.solitairewell.models.Deck

object SettingsContract {
    data class SettingsState(
        val deck: Deck = Deck.FIRST,
        val backCardIndex: Int = 0,
        val backgroundItemIndex: Int = 0,
        val currentLanguage: AppLanguage = AppLanguage.SYSTEM,
        val shouldShowAds: Boolean = true
    ) : UiState

    sealed interface SettingsEvent : UiEvent {
        data object GoBack : SettingsEvent
        data object OnRemoveAds : SettingsEvent
        data class SaveDeck(val deck: Deck) : SettingsEvent
        data class SaveBackCard(val index: Int) : SettingsEvent
        data class SaveBackgroundItem(val index: Int) : SettingsEvent
        data class SaveLanguage(val language: AppLanguage) : SettingsEvent
    }

    sealed interface SettingsAction : UiAction
}