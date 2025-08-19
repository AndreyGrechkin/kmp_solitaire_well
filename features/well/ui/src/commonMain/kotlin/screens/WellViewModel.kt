package screens

import base.Router
import base.TransitionConfig
import base_viewModel.BaseViewModel
import debugLog
import logic.GameSetupFactory
import logic.generateDoubleDeck
import logic.takeFromDeck
import model.CardStack
import model.SlotAddress
import model.SlotType
import models.Card
import models.Screen
import models.UserData

class WellViewModel(
    private val router: Router,
    private val gameSetupFactory: GameSetupFactory
) : BaseViewModel<
        WellContract.WellEvent,
        WellContract.WellState,
        WellContract.WellAction>(
    initialState = WellContract.WellState()
) {

    override suspend fun handleEvent(event: WellContract.WellEvent) {
        when (event) {
            WellContract.WellEvent.OnNewGame -> createNewGame()
            is WellContract.WellEvent.NavigateToSettings -> openSettings()
            is WellContract.WellEvent.ClickCard -> handleClickCard(event.address)
        }
    }

    private fun createNewGame() {
        updateState {
            this.copy(
                stackWells = gameSetupFactory.createNewGame(),
            )
        }
    }

    private fun handleClickCard(address: SlotAddress) {
        debugLog("adress: $address")
    }

    fun openSettings() {
        val settingsData = UserData("Player1", 100)
        router.navigateTo(Screen.Settings(settingsData), TransitionConfig.SLIDE_VERTICAL)
    }

    companion object{
        const val LEFT_INDEX = 0
        const val TOP_INDEX = 1
        const val RIGHT_INDEX = 2
        const val BOTTOM_INDEX = 3
    }
}