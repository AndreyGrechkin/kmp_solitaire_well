package logic

import debugLog
import model.CardStack
import model.SlotAddress
import model.SlotType
import models.Card

class GameSetupFactoryImpl:GameSetupFactory {

    override fun createNewGame(): List<CardStack> {
        val deck = generateDoubleDeck().shuffled().toMutableList()
        debugLog("newDeck: size: ${deck.size}, $deck")
        val innerWells = createInnerWells(deck)
        val externalWells = createExternalWells(deck)
        val stock = listOf(CardStack(deck, SlotAddress(SlotType.STOCK)))
        return innerWells + externalWells + stock
    }

    private fun createInnerWells(deck: MutableList<Card>) : List<CardStack> {
        val innerWells = List(4) { index ->
            CardStack(
                cards = deck.takeFromDeck(10).map { it.copy(isFaceUp = true) },
                address = SlotAddress(SlotType.INNER_WELL, index)
            )
        }
        return innerWells
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

}