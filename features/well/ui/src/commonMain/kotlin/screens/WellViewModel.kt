package screens

import base.Router
import base.TransitionConfig
import base_viewModel.BaseViewModel
import debugLog
import logic.GameSetupFactory
import logic.generateDoubleDeck
import logic.takeFromDeck
import model.CardStack
import model.CardState
import model.GameState
import model.SlotAddress
import model.SlotType
import models.Card
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
        debugLog("adress: $gameState")
        if (gameState.address.type == SlotType.STOCK) {
            handleClickedStock()
        } else {
            when (gameState.state) {
                CardState.DEFAULT -> {
                    if (gameState.state == state.value.gameState.state) {
                        if (gameState.card != null && gameState.address.type != SlotType.STOCK)
                            updateState {
                                this.copy(gameState = gameState.copy(state = CardState.SELECTED))
                            }
                    } else {
                        when (gameState.address.type) {
                            SlotType.FOUNDATION -> {}
                            SlotType.STOCK -> {}
                            SlotType.STOCK_PLAY -> {
                                updateState {
                                    this.copy(gameState = gameState.copy(state = CardState.ERROR))
                                }
                            }

                            SlotType.WASTE -> {}
                            SlotType.INNER_WELL -> {}
                            SlotType.EXTERNAL_WELL -> {}
                        }
                    }
                }

                CardState.SELECTED -> {
                    if (gameState.state == state.value.gameState.state) {
                        if (gameState.card != null && gameState.address.type != SlotType.STOCK)
                            updateState {
                                this.copy(
                                    gameState = gameState.copy(state = CardState.DEFAULT),
                                )
                            }
                    } else {
                        when (gameState.address.type) {
                            SlotType.FOUNDATION -> {}
                            SlotType.STOCK -> {}
                            SlotType.STOCK_PLAY -> {}
                            SlotType.WASTE -> {}
                            SlotType.INNER_WELL -> {}
                            SlotType.EXTERNAL_WELL -> {}
                        }
                    }
                }

                CardState.SUCCESS -> {
                    updateState {
                        this.copy(gameState = gameState.copy(state = CardState.DEFAULT))
                    }
                }

                CardState.ERROR -> {
                    updateState {
                        this.copy(gameState = gameState.copy(state = CardState.DEFAULT))
                    }
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