package screens

import base.Router
import base.TransitionConfig
import base_viewModel.BaseViewModel
import debugLog
import logic.GameSetupFactory
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

    override suspend fun handleEvent(event: WellContract.WellEvent) {
        when (event) {
            WellContract.WellEvent.OnNewGame -> createNewGame()
            is WellContract.WellEvent.NavigateToSettings -> openSettings()
            is WellContract.WellEvent.ClickCard -> handleClickCard(event.state)
        }
    }

    private fun createNewGame() {
        cardsToDealCount = 5
        updateState { this.copy(stackWells = gameSetupFactory.createNewGame()) }
    }

    private fun handleClickCard(gameState: GameState) {
        if (gameState.address.type == SlotType.STOCK) {
            handleClickedStock()
            updateState { this.copy(gameState = gameState.copy(state = CardState.DEFAULT)) }
            return
        }
        val oldGameState = state.value.gameState
        val moveCardResult =
            gameSetupFactory.handleMoveCard(state.value.stackWells, oldGameState, gameState)
        when (moveCardResult) {
            MoveCardResult.Default -> updateState { this.copy(gameState = gameState.copy(state = CardState.DEFAULT)) }
            MoveCardResult.Error -> updateState { this.copy(gameState = gameState.copy(state = CardState.ERROR)) }
            MoveCardResult.Selected -> updateState { this.copy(gameState = gameState.copy(state = CardState.SELECTED)) }
            is MoveCardResult.Success -> {
                updateState {
                    this.copy(
                        stackWells = moveCardResult.newStacks,
                        gameState = GameState(
                            card = oldGameState.card,
                            address = gameState.address,
                            state = CardState.SUCCESS
                        ),
                    )
                }
            }
        }
    }

    private fun handleClickedStock() {
        val currentStacks = state.value.stackWells
        val result = gameSetupFactory.handleStockClick(currentStacks, cardsToDealCount)

        // Обновляем состояние во ViewModel
        cardsToDealCount = result.newDealCount
        updateState { this.copy(stackWells = result.updatedStacks) }

        // Проверяем завершение пасьянса
        if (cardsToDealCount == 0) {
            debugLog("Пасьянс завершен")
            // TODO: Обработка завершения игры
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