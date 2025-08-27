package screens

import base.Router
import base.TransitionConfig
import base_viewModel.BaseViewModel
import debugLog
import logic.GameSetupFactory
import model.CardStack
import model.CardState
import model.GameState
import model.MoveCardResult
import model.SlotType
import models.Screen
import models.UserData

class WellViewModel(
    private val router: Router,
    private val gameSetupFactory: GameSetupFactory,
) : BaseViewModel<
        WellContract.WellEvent,
        WellContract.WellState,
        WellContract.WellAction>(
    initialState = WellContract.WellState()
) {
    private var cardsToDealCount = 5
    private var prevStackWells: List<CardStack> = emptyList()
    private var prevGameState: GameState = state.value.gameState
    private var countStep = 0

    override suspend fun handleEvent(event: WellContract.WellEvent) {
        when (event) {
            WellContract.WellEvent.OnNewGame -> createNewGame()
            WellContract.WellEvent.OnBackMove -> {
                countStep = countStep + 1
                updateState {
                    this.copy(
                        stackWells = prevStackWells,
                        gameState = gameState.copy(state = CardState.DEFAULT),
                        gameMessage = "Ход: $countStep",
                        isEnabled = false
                    )
                }
            }

            is WellContract.WellEvent.NavigateToSettings -> openSettings()
            is WellContract.WellEvent.OnClickCard -> handleClickCard(event.state)
            is WellContract.WellEvent.OnAnimationFinished -> handleFinishedAnimation()
            WellContract.WellEvent.OnHelp -> {

            }
        }
    }

    private fun createNewGame() {
        cardsToDealCount = 5
        updateState { this.copy(
            stackWells = gameSetupFactory.createNewGame(),
            gameMessage = "Ход: $countStep"
        ) }
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
        prevStackWells = state.value.stackWells
        prevGameState = state.value.gameState

        if (gameState.address.type == SlotType.STOCK) {
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
                this.copy(gameState = gameState.copy(state = CardState.DEFAULT)) }
            MoveCardResult.Error -> updateState {
                debugLog("click: Error")
                countStep = countStep + 1
                this.copy(
                    gameState = gameState.copy(state = CardState.ERROR),
                    gameMessage = "Ход: $countStep"
                )
            }
            MoveCardResult.Selected -> updateState {
                debugLog("click: Select")
                this.copy(gameState = gameState.copy(state = CardState.SELECTED)) }
            is MoveCardResult.Success -> {
                debugLog("click: Success")
                countStep = countStep + 1
                val validGame =  moveCardResult.newStacks
                val gameVin = validGame
                    .filter { it.address.type == SlotType.FOUNDATION }
                    .sumOf { it.cards.size }

                updateState {
                    this.copy(
                        stackWells = moveCardResult.newStacks,
                        gameState = GameState(
                            cards = oldGameState.cards,
                            address = gameState.address,
                            state = CardState.SUCCESS
                        ),
                        gameMessage = if (gameVin == 104) "Победа!!!" else "Ход: $countStep"
                    )
                }
            }
        }
    }

    private fun handleClickedStock() {
        val currentStacks = state.value.stackWells
        val result = gameSetupFactory.handleStockClick(currentStacks, cardsToDealCount)
        cardsToDealCount = result.newDealCount

        updateState {
            this.copy(
                stackWells = result.updatedStacks,
                gameState = gameState.copy(state = CardState.DEFAULT),
                isEnabled = true,
                gameMessage = if (cardsToDealCount == 0) "Пичалька" else "Ход: $countStep"
            )
        }
        // Обновляем состояние во ViewModel



        // Проверяем завершение пасьянса
        if (cardsToDealCount == 0) {
            debugLog("Пасьянс завершен")

        }
    }

    fun openSettings() {
        val settingsData = UserData("Player1", 100)
        router.navigateTo(Screen.Settings(settingsData), TransitionConfig.SLIDE_VERTICAL)
    }

    companion object {
        const val LEFT_INDEX = 0
        const val TOP_INDEX = 1
        const val RIGHT_INDEX = 2
        const val BOTTOM_INDEX = 3
    }
}