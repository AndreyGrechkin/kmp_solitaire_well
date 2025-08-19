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
}