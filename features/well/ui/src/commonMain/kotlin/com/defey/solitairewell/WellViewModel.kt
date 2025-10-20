package com.defey.solitairewell

import base.Router
import base.TransitionConfig
import base_viewModel.BaseViewModel
import debugLog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import logic.CommonTimer
import logic.GameSetupFactory
import model.CardState
import model.GameState
import model.MoveCardResult
import models.Deck
import models.Screen
import models.UserData
import models.WellCardStack
import models.WellGameState
import models.WellSlotType
import repository.StorageRepository
import repository.WellRepository

class WellViewModel(
    private val router: Router,
    private val gameSetupFactory: GameSetupFactory,
    private val timer: CommonTimer,
    private val wellRepository: WellRepository,
    private val storageRepository: StorageRepository,
) : BaseViewModel<
        WellContract.WellEvent,
        WellContract.WellState,
        WellContract.WellAction>(
    initialState = WellContract.WellState()
) {
    private var cardsToDealCount = 5
    private var prevStackWells: List<WellCardStack> = emptyList()
    private var prevGameState: GameState = state.value.gameState
    private var countStep = 0

    init {
        observeDeck()
        observeBackCard()
        handleLoadCardStack()
    }

    override suspend fun handleEvent(event: WellContract.WellEvent) {
        when (event) {
            is WellContract.WellEvent.OnClickCard -> handleClickCard(event.state)
            is WellContract.WellEvent.OnAnimationFinished -> handleFinishedAnimation()
            WellContract.WellEvent.OnLoadGame -> loadCardStack()
            is WellContract.WellEvent.OnMenu -> handleClickMenu(event.menu)
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

    private fun handleBackMove() {
        countStep = countStep + 1
        updateState {
            this.copy(
                stackWells = prevStackWells,
                gameState = gameState.copy(state = CardState.DEFAULT),
                gameMessage = "Ход: $countStep",
                availableBackMove = prevStackWells == state.value.stackWells,
                hintState = emptyList()
            )
        }
        saveCardStack()
    }

    private fun showRules() {
        viewModelScope.launch {
            sendAction(WellContract.WellAction.ShowRulesDialog)
        }
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

    private fun saveCardStack() {
        viewModelScope.launch {
            wellRepository.setWellGameState(
                WellGameState(
                    countGameStack = cardsToDealCount,
                    step = countStep
                )
            )
            wellRepository.setWellCards(state.value.stackWells)
            debugLog("save card2: ${cardsToDealCount}")
        }
    }

    private fun loadCardStack() {
        viewModelScope.launch {
            val cardStack = wellRepository.getWellCards()
            val gameState = wellRepository.getWellGameState()
            cardsToDealCount = gameState.countGameStack ?: 5
            countStep = gameState.step ?: 0
            debugLog("load card2: ${cardsToDealCount}, ${cardStack.filter { it.address.type == WellSlotType.STOCK_PLAY }}")
            updateState {
                this.copy(stackWells = cardStack)
            }
        }
    }

    private fun deleteCardStack() {
        viewModelScope.launch {
            wellRepository.deleteWellGameState()
            wellRepository.deleteWellCards()
        }
    }

    private fun handleClickHelp() {
        createHintTimer()
        updateState {
            debugLog("click: Help")
            this.copy(
                gameState = gameState.copy(state = CardState.DEFAULT),
                availableHint = false,
                hintState = gameSetupFactory.handleHint(state.value.stackWells)
            )
        }
    }

    private fun createNewGame() {
        cardsToDealCount = 5
        deleteCardStack()
        timer.dispose()
        updateState {
            this.copy(
                stackWells = gameSetupFactory.createNewGame(),
                gameMessage = "Ход: $countStep",
                hintState = emptyList(),
                availableHint = true,
                availableBackMove = false
            )
        }
    }

    private fun handleFinishedAnimation() {
        updateState { this.copy(gameState = gameState.copy(state = CardState.DEFAULT)) }
    }

    private fun updateGameMessage(text: String) {
        updateState {
            this.copy(gameMessage = text)
        }
    }

    private fun handleClickCard(gameState: GameState) {
        if (state.value.stackWells.isEmpty()) return
        debugLog("click card2: ${cardsToDealCount}")
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
                debugLog("click: Default")
                this.copy(
                    gameState = gameState.copy(state = CardState.DEFAULT),
                    hintState = emptyList()
                )
            }

            MoveCardResult.Error -> updateState {
                debugLog("click: Error")
                countStep = countStep + 1
                this.copy(
                    gameState = gameState.copy(state = CardState.ERROR),
                    gameMessage = "Ход: $countStep",
                    hintState = emptyList()
                )
            }

            MoveCardResult.Selected -> updateState {
                debugLog("click: Select")
                this.copy(
                    gameState = gameState.copy(state = CardState.SELECTED),
                    hintState = emptyList()
                )
            }

            is MoveCardResult.Success -> {
                prevStackWells = state.value.stackWells
                prevGameState = state.value.gameState

                debugLog("click: Success")
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
                        gameMessage = if (gameVin == 104) "Победа!!!" else "Ход: $countStep",
                        hintState = emptyList()
                    )
                }
                if (gameVin == 104) deleteCardStack() else saveCardStack()
            }
        }
    }

    private fun handleClickedStock() {
        val currentStacks = state.value.stackWells
        debugLog(
            "stock click: ${
                currentStacks.filter { it.address.type == WellSlotType.STOCK_PLAY }
                    .map { it.address }
            }"
        )
        val result = gameSetupFactory.handleStockClick(currentStacks, cardsToDealCount)
        cardsToDealCount = result.newDealCount
        prevStackWells = state.value.stackWells
        prevGameState = state.value.gameState
        debugLog(
            "stock click2: ${
                result.updatedStacks.filter { it.address.type == WellSlotType.STOCK_PLAY }
                    .map { it.address }
            }"
        )
        updateState {
            this.copy(
                stackWells = result.updatedStacks,
                gameState = gameState.copy(state = CardState.DEFAULT),
                availableBackMove = prevStackWells != result.updatedStacks,
                gameMessage = if (cardsToDealCount == 0) "Пичалька" else "Ход: $countStep",
                hintState = emptyList()
            )
        }
        if (cardsToDealCount == 0) deleteCardStack() else saveCardStack()
        if (cardsToDealCount == 0) {
            debugLog("Пасьянс завершен")

        }
    }

    private fun createHintTimer() {
        CommonTimer.Companion.create()
        timer.start(
            60000,
            onTick = {
                debugLog("tick: ${it}")
            },
            onFinish = {
                updateState {
                    this.copy(
                        availableHint = true
                    )
                }
            }
        )
    }

    fun openSettings() {
        val settingsData = UserData("Player1", 100)
        router.navigateTo(Screen.Settings(settingsData), TransitionConfig.Companion.SLIDE_VERTICAL)
    }

    override fun onCleared() {
        saveCardStack()
        super.onCleared()
        timer.dispose()
    }

    companion object {
        const val LEFT_INDEX = 0
        const val TOP_INDEX = 1
        const val RIGHT_INDEX = 2
        const val BOTTOM_INDEX = 3
    }
}