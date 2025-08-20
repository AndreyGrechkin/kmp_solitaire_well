package logic

import debugLog
import model.CardStack
import model.SlotAddress
import model.SlotType
import model.StockClickResult
import models.Card

class GameSetupFactoryImpl:GameSetupFactory {

    override fun createNewGame(): List<CardStack> {
        val deck = generateDoubleDeck().shuffled().toMutableList()
//        debugLog("newDeck: size: ${deck.size}, $deck")
        val innerWells = createInnerWells(deck)
        val externalWells = createExternalWells(deck)
        val stock = listOf(CardStack(deck, SlotAddress(SlotType.STOCK)))
        return innerWells + externalWells + stock
    }

    override fun handleStockClick(
        currentStacks: List<CardStack>,
        currentDealCount: Int,
    ): StockClickResult {
        val mutableStacks = currentStacks.toMutableList()

        val stockStack = mutableStacks.find { it.address.type == SlotType.STOCK }
        val stockPlayStacks = mutableStacks.filter { it.address.type == SlotType.STOCK_PLAY }

        return if (stockStack != null && stockStack.cards.isNotEmpty()) {
            // Выкладываем карты из колоды
            val updatedStacks = dealNewCardsOnTop(stockStack, stockPlayStacks, mutableStacks, currentDealCount)
            StockClickResult(updatedStacks, currentDealCount)
        } else {
            // Колода пуста - ресет и НЕМЕДЛЕННОЕ выкладывание
            resetStockToDeck(stockPlayStacks, mutableStacks, currentDealCount)
        }
    }

    private fun dealNewCardsOnTop(
        stock: CardStack,
        stockPlayStacks: List<CardStack>,
        currentStacks: MutableList<CardStack>,
        dealCount: Int
    ): List<CardStack> {
        val cardsToDeal = stock.cards.take(dealCount).map { it.copy(isFaceUp = true) }
        val remainingCardsInStock = stock.cards.drop(dealCount)

        val updatedStock = stock.copy(cards = remainingCardsInStock)
        val updatedStockPlay = updateStockPlayStacks(stockPlayStacks, cardsToDeal)

        return currentStacks.apply {
            removeAll { it.address.type == SlotType.STOCK || it.address.type == SlotType.STOCK_PLAY }
            add(updatedStock)
            addAll(updatedStockPlay)
        }
    }

    private fun resetStockToDeck(
        stockPlayStacks: List<CardStack>,
        currentStacks: MutableList<CardStack>,
        currentDealCount: Int
    ): StockClickResult {
        val allCardsFromStockPlay = stockPlayStacks.flatMap { it.cards }

        if (allCardsFromStockPlay.isEmpty()) {
            debugLog("Пасьянс завершен - нет карт для ресета")
            return StockClickResult(currentStacks, currentDealCount)
        }

        // Уменьшаем количество карт для следующего выкладывания
        val newDealCount = if (currentDealCount > 1) {
            currentDealCount - 1
        } else {
            debugLog("Пасьянс завершен - нечего выкладывать")
            return StockClickResult(currentStacks, 0) // 0 означает завершение
        }

        debugLog("Уменьшаем количество карт для выкладывания: $newDealCount")

        // Переворачиваем все карты рубашкой вверх
        val resetCards = allCardsFromStockPlay.map { it.copy(isFaceUp = false) }

        // Создаем новую колоду
        val newStock = CardStack(cards = resetCards, address = SlotAddress(SlotType.STOCK))

        // НЕМЕДЛЕННО выкладываем карты из новой колоды
        val cardsToDeal = resetCards.take(newDealCount).map { it.copy(isFaceUp = true) }
        val remainingCardsInStock = resetCards.drop(newDealCount)

        val finalStock = if (remainingCardsInStock.isNotEmpty()) {
            newStock.copy(cards = remainingCardsInStock)
        } else {
            null // Колода пуста
        }

        // Создаем новые STOCK_PLAY стопки
        val updatedStockPlay = cardsToDeal.mapIndexed { index, card ->
            CardStack(cards = listOf(card), address = SlotAddress(SlotType.STOCK_PLAY, index))
        }

        // Обновляем общий список стопок
        val updatedStacks = currentStacks.apply {
            removeAll { it.address.type == SlotType.STOCK || it.address.type == SlotType.STOCK_PLAY }
            if (finalStock != null) {
                add(finalStock) // Добавляем колоду если там есть карты
            }
            addAll(updatedStockPlay) // Добавляем выложенные карты
        }

        return StockClickResult(updatedStacks, newDealCount)
    }

    private fun updateStockPlayStacks(
        existingStacks: List<CardStack>,
        newCards: List<Card>
    ): List<CardStack> {
        return if (existingStacks.isNotEmpty()) {
            existingStacks.mapIndexed { index, stack ->
                if (index < newCards.size) {
                    val newTopCard = newCards[index]
                    stack.copy(cards = stack.cards + newTopCard)
                } else {
                    stack
                }
            }
        } else {
            newCards.mapIndexed { index, card ->
                CardStack(cards = listOf(card), address = SlotAddress(SlotType.STOCK_PLAY, index))
            }
        }
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