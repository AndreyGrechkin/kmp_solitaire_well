package setting

import base_viewModel.BaseViewModel
import base.Router
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import models.Deck
import repository.StorageRepository

class SettingsViewModel(
    private val router: Router,
    private val storageRepository: StorageRepository
) : BaseViewModel<
        SettingsContract.SettingsEvent,
        SettingsContract.SettingsState,
        SettingsContract.SettingsAction>(
    initialState = SettingsContract.SettingsState()
) {

    init {
        observeDeck()
        observeBackCard()
    }

    override suspend fun handleEvent(event: SettingsContract.SettingsEvent) {
        when (event) {
            is SettingsContract.SettingsEvent.GoBack -> openWell()
            is SettingsContract.SettingsEvent.SaveDeck -> saveDeck(event.deck)
            is SettingsContract.SettingsEvent.SaveBackCard -> saveBackCard(event.index)
        }
    }

    private fun saveBackCard(index: Int) {
        viewModelScope.launch {
            storageRepository.setBackCard(index)
        }

    }

    private fun observeBackCard() {
        storageRepository.getBackCardFlow().onEach { response ->
            updateState {
                this.copy(backCardIndex = response)
            }
        }.launchIn(viewModelScope)
    }

    private fun observeDeck() {
        storageRepository.getDeckFlow().onEach { response ->
            updateState {
                val deck = response ?: Deck.FIRST
                this.copy(deck = deck)
            }
        }.launchIn(viewModelScope)
    }

    private fun saveDeck(deck: Deck) {
        viewModelScope.launch {
            storageRepository.setDeck(deck)
        }
    }

    fun openWell() {
        router.navigateBack()
    }

}