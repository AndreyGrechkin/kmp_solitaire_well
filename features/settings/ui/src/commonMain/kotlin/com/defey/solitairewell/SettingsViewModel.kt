package com.defey.solitairewell

import androidx.lifecycle.SavedStateHandle
import com.defey.solitairewell.base.NavigationManager
import com.defey.solitairewell.base_viewModel.BaseViewModel
import com.defey.solitairewell.managers.AppLanguage
import com.defey.solitairewell.managers.LanguageManager
import com.defey.solitairewell.managers.billing.ProductsRepository
import com.defey.solitairewell.managers.billing.PurchasesRepository
import com.defey.solitairewell.managers.billing.StorePreferredPurchaseType
import com.defey.solitairewell.models.Deck
import com.defey.solitairewell.models.ProductArticle
import com.defey.solitairewell.repository.StorageRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsViewModel(
    savedStateHandle: SavedStateHandle,
    private val navigationManager: NavigationManager,
    private val storageRepository: StorageRepository,
    private val languageManager: LanguageManager,
    private val productsRepository: ProductsRepository,
    private val purchasesRepository: PurchasesRepository,
) : BaseViewModel<
        SettingsContract.SettingsEvent,
        SettingsContract.SettingsState,
        SettingsContract.SettingsAction>(
    initialState = SettingsContract.SettingsState()
) {

    init {
        val userName = savedStateHandle.get<String>("userName") ?: ""
        val score = savedStateHandle.get<Int>("score") ?: 0
        observeDeck()
        observeBackCard()
        observeBackgroundIndex()
        observeLanguage()
        observeAds()
    }

    override suspend fun handleEvent(event: SettingsContract.SettingsEvent) {
        when (event) {
            is SettingsContract.SettingsEvent.GoBack -> openWell()
            is SettingsContract.SettingsEvent.SaveDeck -> saveDeck(event.deck)
            is SettingsContract.SettingsEvent.SaveBackCard -> saveBackCard(event.index)
            is SettingsContract.SettingsEvent.SaveBackgroundItem -> saveBackground(event.index)
            is SettingsContract.SettingsEvent.SaveLanguage -> saveLanguage(event.language)
            SettingsContract.SettingsEvent.OnRemoveAds -> buyRemoveAds()
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

    private fun observeLanguage() {
        languageManager.languageFlow.onEach { language ->
            updateState {
                this.copy(currentLanguage = language)
            }
        }.launchIn(viewModelScope)
    }

    private fun saveLanguage(language: AppLanguage) {
        languageManager.setLanguage(language)
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

    private fun observeAds() {
        storageRepository.getRemoveAdsFlow().onEach { response ->
            updateState {
                this.copy(shouldShowAds = !response)
            }
        }.launchIn(viewModelScope)
    }

    private fun saveDeck(deck: Deck) {
        viewModelScope.launch {
            storageRepository.setDeck(deck)
        }
    }

    private fun openWell() {
        navigationManager.popBackStack()
    }

    private fun buyRemoveAds(purchaseType: StorePreferredPurchaseType = StorePreferredPurchaseType.ONE_STEP) {
        viewModelScope.launch {
            runCatching {
                purchasesRepository.purchase(ProductArticle.REMOVE_ADS, purchaseType)
            }.onSuccess {
                val isCheck = productsRepository.checkPurchasedProduct(ProductArticle.REMOVE_ADS)
                if (isCheck) storageRepository.setRemoveAds()

            }.onFailure { error ->
            }
        }
    }

}