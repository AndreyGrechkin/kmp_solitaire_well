package com.defey.solitairewell

import com.defey.solitairewell.base.NavigationManager
import com.defey.solitairewell.base_viewModel.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.defey.solitairewell.logic.CommonTimer
import com.defey.solitairewell.logic.GameSetupFactory
import com.defey.solitairewell.managers.ads.AdManager
import com.defey.solitairewell.managers.billing.ProductsRepository
import com.defey.solitairewell.model.CardState
import com.defey.solitairewell.model.GameState
import com.defey.solitairewell.model.MoveCardResult
import com.defey.solitairewell.models.Deck
import com.defey.solitairewell.models.GameFinishStatus
import com.defey.solitairewell.models.ProductArticle
import com.defey.solitairewell.models.Screen
import com.defey.solitairewell.models.UserData
import com.defey.solitairewell.models.WellCardStack
import com.defey.solitairewell.models.WellGameState
import com.defey.solitairewell.models.WellSlotType
import com.defey.solitairewell.repository.StorageRepository
import com.defey.solitairewell.repository.WellRepository

class WellViewModel(
    private val navigationManager: NavigationManager,
    private val gameSetupFactory: GameSetupFactory,
    private val timer: CommonTimer,
    private val wellRepository: WellRepository,
    private val storageRepository: StorageRepository,
    private val adManager: AdManager,
    private val productsRepository: ProductsRepository,
) : BaseViewModel<
        WellContract.WellEvent,
        WellContract.WellState,
        WellContract.WellAction>(
    initialState = WellContract.WellState()
) {
    private var cardsToDealCount = CARDS_TO_DEAL
    private var prevStackWells: List<WellCardStack> = emptyList()
    private var prevGameState: GameState = state.value.gameState
    private var countStep = 0

    init {
        observeDeck()
        observeBackCard()
        observeBackgroundIndex()
        observeAds()
        handleLoadCardStack()
    }

    override suspend fun handleEvent(event: WellContract.WellEvent) {
        when (event) {
            WellContract.WellEvent.OnLoadGame -> loadCardStack()
            is WellContract.WellEvent.OnClickCard -> handleClickCard(event.state)
            is WellContract.WellEvent.OnAnimationFinished -> handleFinishedAnimation()
            is WellContract.WellEvent.OnMenu -> handleClickMenu(event.menu)
            WellContract.WellEvent.OnFinishGame -> {
                if (state.value.shouldShowAds) adManager.onGameFinished()
            }
        }
    }

    private fun observeDeck() {
        storageRepository.getDeckFlow().onEach { response ->
            updateState {
                val deck = response ?: Deck.FIRST
                this.copy(deck = deck)
            }
        }.launchIn(viewModelScope)
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

    private fun observeAds() {
        storageRepository.getRemoveAdsFlow().onEach { response ->
            if (!response) {
                val isCheck = productsRepository.checkPurchasedProduct(ProductArticle.REMOVE_ADS)
                if (isCheck) storageRepository.setRemoveAds()
            }
            updateState { this.copy(shouldShowAds = !response) }
        }.launchIn(viewModelScope)
    }

    private fun handleLoadCardStack() {
        viewModelScope.launch {
            val cardStack = wellRepository.getWellCards()
            if (cardStack.isEmpty()) createNewGame()
            else {
                sendAction(WellContract.WellAction.ShowRenewalDialog)
            }
        }
    }

    private fun handleClickMenu(menu: WellMenu) {
        when (menu) {
            WellMenu.NEW_GAME -> createNewGame()
            WellMenu.BACK_MOVE -> handleBackMove()
            WellMenu.HELP -> handleClickHelp()
            WellMenu.SETTINGS -> openSettings()
            WellMenu.RULES -> showRules()
            WellMenu.OTHER_GAMES -> {}
        }
    }

    private fun createNewGame() {
        cardsToDealCount = CARDS_TO_DEAL
        countStep = 0
        deleteCardStack()
        timer.dispose()
        updateState {
            this.copy(
                stackWells = gameSetupFactory.createNewGame(),
                gameStep = countStep,
                hintState = emptyList(),
                availableHint = true,
                availableBackMove = false
            )
        }
    }

    private fun handleBackMove() {
        countStep = countStep + 1
        updateState {
            this.copy(
                stackWells = prevStackWells,
                gameState = gameState.copy(state = CardState.DEFAULT),
                gameStep = countStep,
                availableBackMove = prevStackWells == state.value.stackWells,
                hintState = emptyList()
            )
        }
        saveCardStack()
    }

    private fun handleClickHelp() {
        createHintTimer()
        updateState {
            this.copy(
                gameState = gameState.copy(state = CardState.DEFAULT),
                availableHint = false,
                hintState = gameSetupFactory.handleHint(state.value.stackWells)
            )
        }
    }

    fun openSettings() {
        val settingsData = UserData("Player1", 100)
        navigationManager.navigate(
            Screen.Settings.createRoute(
                userName = settingsData.username,
                score = settingsData.score
            )
        )
    }

    private fun showRules() {
        viewModelScope.launch {
            sendAction(WellContract.WellAction.ShowRulesDialog)
        }
    }

    private fun loadCardStack() {
        viewModelScope.launch {
            val cardStack = wellRepository.getWellCards()
            val gameState = wellRepository.getWellGameState()
            cardsToDealCount = gameState.countGameStack ?: CARDS_TO_DEAL
            countStep = gameState.step ?: 0
            updateState {
                this.copy(
                    stackWells = cardStack,
                    gameStep = countStep
                )
            }
        }
    }

    private fun handleClickCard(gameState: GameState) {
        if (state.value.stackWells.isEmpty()) return
        if (gameState.address.type == WellSlotType.STOCK) {
            countStep = countStep + 1
            handleClickedStock()
            return
        }
        val oldGameState = state.value.gameState
        val moveCardResult =
            gameSetupFactory.handleMoveCard(state.value.stackWells, oldGameState, gameState)
        when (moveCardResult) {
            MoveCardResult.Default -> updateState {
                this.copy(
                    gameState = gameState.copy(state = CardState.DEFAULT),
                    hintState = emptyList()
                )
            }

            MoveCardResult.Error -> updateState {
                countStep = countStep + 1
                this.copy(
                    gameState = gameState.copy(state = CardState.ERROR),
                    gameStep = countStep,
                    hintState = emptyList()
                )
            }

            MoveCardResult.Selected -> updateState {
                this.copy(
                    gameState = gameState.copy(state = CardState.SELECTED),
                    hintState = emptyList()
                )
            }

            is MoveCardResult.Success -> {
                prevStackWells = state.value.stackWells
                prevGameState = state.value.gameState
                countStep = countStep + 1
                val validGame = moveCardResult.newStacks
                val gameVin = validGame
                    .filter { it.address.type == WellSlotType.FOUNDATION }
                    .sumOf { it.cards.size }

                updateState {
                    this.copy(
                        stackWells = moveCardResult.newStacks,
                        gameState = GameState(
                            card = oldGameState.card,
                            address = gameState.address,
                            state = CardState.SUCCESS
                        ),
                        availableBackMove = prevStackWells != moveCardResult.newStacks,
                        gameStep = countStep,
                        hintState = emptyList()
                    )
                }
                if (gameVin == MAX_DECK_COUNT) {
                    handleFinishGame(GameFinishStatus.WIN)
                    deleteCardStack()
                } else saveCardStack()
            }
        }
    }

    private fun handleFinishedAnimation() {
        updateState { this.copy(gameState = gameState.copy(state = CardState.DEFAULT)) }
    }

    private fun handleFinishGame(status: GameFinishStatus) {
        viewModelScope.launch {
            sendAction(WellContract.WellAction.ShowWinDialog(status))
        }
    }

    private fun handleClickedStock() {
        val currentStacks = state.value.stackWells
        val result = gameSetupFactory.handleStockClick(currentStacks, cardsToDealCount)
        cardsToDealCount = result.newDealCount
        prevStackWells = state.value.stackWells
        prevGameState = state.value.gameState
        updateState {
            this.copy(
                stackWells = result.updatedStacks,
                gameState = gameState.copy(state = CardState.DEFAULT),
                availableBackMove = prevStackWells != result.updatedStacks,
                gameStep = countStep,
                hintState = emptyList()
            )
        }
        if (cardsToDealCount == 0) {
            handleFinishGame(GameFinishStatus.LOSE)
            deleteCardStack()
        } else saveCardStack()
    }

    private fun saveCardStack() {
        viewModelScope.launch {
            wellRepository.setWellGameState(
                WellGameState(
                    countGameStack = cardsToDealCount,
                    step = countStep
                )
            )
            wellRepository.setWellCards(state.value.stackWells)
        }
    }

    private fun deleteCardStack() {
        viewModelScope.launch {
            wellRepository.deleteWellGameState()
            wellRepository.deleteWellCards()
        }
    }

    private fun createHintTimer() {
        CommonTimer.Companion.create()
        timer.start(
            durationMillis = DURATION_HINT_TIME,
            onFinish = { updateState { this.copy(availableHint = true) } }
        )
    }

    override fun onCleared() {
        saveCardStack()
        super.onCleared()
        timer.dispose()
    }

    companion object {
        private const val CARDS_TO_DEAL = 5
        private const val MAX_DECK_COUNT = 104
        private const val DURATION_HINT_TIME = 60000L
        const val LEFT_INDEX = 0
        const val TOP_INDEX = 1
        const val RIGHT_INDEX = 2
        const val BOTTOM_INDEX = 3
    }
}