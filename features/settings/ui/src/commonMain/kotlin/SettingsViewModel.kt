import base.NavigationManager
import base_viewModel.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import managers.AppLanguage
import managers.LanguageManager
import models.Deck
import repository.StorageRepository

class SettingsViewModel(
    private val navigationManager: NavigationManager,
    private val storageRepository: StorageRepository,
    private val languageManager: LanguageManager,
) : BaseViewModel<
        SettingsContract.SettingsEvent,
        SettingsContract.SettingsState,
        SettingsContract.SettingsAction>(
    initialState = SettingsContract.SettingsState()
) {

    init {
        debugLog("Setting init")
        observeDeck()
        observeBackCard()
        observeBackgroundIndex()
        getCurrentLanguage()
    }

    override suspend fun handleEvent(event: SettingsContract.SettingsEvent) {
        when (event) {
            is SettingsContract.SettingsEvent.GoBack -> openWell()
            is SettingsContract.SettingsEvent.SaveDeck -> saveDeck(event.deck)
            is SettingsContract.SettingsEvent.SaveBackCard -> saveBackCard(event.index)
            is SettingsContract.SettingsEvent.SaveBackgroundItem -> saveBackground(event.index)
            is SettingsContract.SettingsEvent.SaveLanguage -> saveLanguage(event.language)
        }
    }

    private fun saveBackCard(index: Int) {
        viewModelScope.launch {
            storageRepository.setBackCard(index)
        }
    }

    private fun saveBackground(index: Int) {
        viewModelScope.launch {
            storageRepository.setBackgroundIndex(index)
        }
    }

    private fun getCurrentLanguage() {
        val currentLanguage = languageManager.currentLanguage
        debugLog("curLang: ${currentLanguage}")
        updateState {
            this.copy(currentLanguage = currentLanguage)
        }
    }

    private fun saveLanguage(language: AppLanguage) {
        languageManager.setLanguage(language)
        getCurrentLanguage()
    }

    private fun observeBackCard() {
        storageRepository.getBackCardFlow().onEach { response ->
            updateState {
                this.copy(backCardIndex = response)
            }
        }.launchIn(viewModelScope)
    }

    private fun observeBackgroundIndex() {
        storageRepository.getBackgroundIndexFlow().onEach { response ->
            updateState {
                this.copy(backgroundItemIndex = response)
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
        navigationManager.popBackStack()
    }

    override fun onCleared() {
        super.onCleared()
        debugLog("Setting clear")
    }

}