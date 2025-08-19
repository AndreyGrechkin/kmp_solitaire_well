package screens

import base.Router
import base.TransitionConfig
import base_viewModel.BaseViewModel
import debugLog
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
        val deck = generateDoubleDeck().shuffled().toMutableList()
        debugLog("newDeck: size: ${deck.size}, $deck")

        // 2. Формируем внутренние стопки (пока не переворачиваем)
        val innerWells = createInnerWells(deck)

        // 3. Создаем внешние стопки (уже перевернутые)
        val externalWells = createExternalWells(deck)

        val stackWells = innerWells + externalWells
        // 5. Только теперь обновляем состояние
        updateState {
            this.copy(
                stackWells = stackWells,
                stock = CardStack(deck, SlotAddress(SlotType.STOCK))
            )
        }
    }

    private fun createInnerWells(deck: MutableList<Card>) : List<CardStack> {
       val innerWells = List(4) { index ->
            CardStack(
                cards = deck.takeFromDeck(10),
                address = SlotAddress(SlotType.INNER_WELL, index)
            )
        }
        return flipTopCards(innerWells)
    }

    private fun createExternalWells(deck: MutableList<Card>) : List<CardStack> {
        return List(4) { index ->
            val topCard = deck.removeFirst().copy(isFaceUp = true)
            CardStack(
                cards = listOf(topCard),
                address = SlotAddress(SlotType.EXTERNAL_WELL, index)
            )
        }
    }

    private fun flipTopCards(stacks: List<CardStack>): List<CardStack> {
        return stacks.map { stack ->
            if (stack.cards.isNotEmpty()) {
                val updatedCards = stack.cards.toMutableList().apply {
                    set(lastIndex, last().copy(isFaceUp = true))
                }
                stack.copy(cards = updatedCards)
            } else {
                stack
            }
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