package logic

import debugLog
import model.CardStack
import model.CardState
import model.GameState
import model.MoveCardResult
import model.SlotAddress
import model.SlotType
import model.StockClickResult
import models.Card
import models.Rank
import models.Suit

class GameSetupFactoryImpl : GameSetupFactory {

    override fun createNewGame(): List<CardStack> {
        val deck = generateDoubleDeck().shuffled().toMutableList()
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
            val updatedStacks =
                dealNewCardsOnTop(stockStack, stockPlayStacks, mutableStacks, currentDealCount)
            StockClickResult(updatedStacks, currentDealCount)
        } else {
            resetStockToDeck(stockPlayStacks, mutableStacks, currentDealCount)
        }
    }

    override fun handleMoveCard(
        currentStacks: List<CardStack>,
        oldGameState: GameState,
        currentGameState: GameState,
    ): MoveCardResult {
        return when (currentGameState.state) {
            CardState.DEFAULT -> {
                if (currentGameState.state == oldGameState.state) {
                    if (currentGameState.card != null
                        && currentGameState.address.type !in setOf(
                            SlotType.STOCK,
                            SlotType.FOUNDATION
                        )
                    )
                        MoveCardResult.Selected else MoveCardResult.Default
                } else {
                    handleValidMove(currentStacks.toMutableList(), oldGameState, currentGameState)
                }
            }

            CardState.SELECTED,
            CardState.SUCCESS,
            CardState.ERROR -> MoveCardResult.Default
        }
    }

    private fun handleValidMove(
        currentDeck: MutableList<CardStack>,
        oldGameState: GameState,
        gameState: GameState,
    ): MoveCardResult {
        return when (gameState.address.type) {
            SlotType.FOUNDATION -> {
                val foundSuit = currentDeck
                    .filter { it.address.type == SlotType.FOUNDATION }
                    .mapNotNull { stack -> stack.cards.firstOrNull()?.suit }
                val valid = validationCardOnFoundation(oldGameState.card, gameState.card, foundSuit)
                handleCardMove(valid, currentDeck, oldGameState, gameState)
            }

            SlotType.WASTE -> {
                val valid = validationCardOnWaste(oldGameState.card, gameState.card)
                handleCardMove(valid, currentDeck, oldGameState, gameState)
            }

            SlotType.EXTERNAL_WELL -> {
                val valid = validationCardOnExternal(oldGameState, gameState)
                handleCardMove(valid, currentDeck, oldGameState, gameState)
            }

            SlotType.INNER_WELL,
            SlotType.STOCK_PLAY -> MoveCardResult.Error

            SlotType.STOCK -> MoveCardResult.Default
        }
    }

    private fun handleCardMove(
        isValid: Boolean,
        currentDeck: MutableList<CardStack>,
        oldGameState: GameState,
        gameState: GameState,
    ): MoveCardResult {
        return if (isValid) {
            val newDeck = moveCardToNewAddress(currentDeck, oldGameState, gameState)
            MoveCardResult.Success(newDeck)
        } else MoveCardResult.Error
    }

    fun moveCardToNewAddress(
        mutableStacks: MutableList<CardStack>,
        oldGameState: GameState,
        gameState: GameState,
    ): List<CardStack> {

        val cardToMove = oldGameState.card ?: return mutableStacks
        val targetAddress = gameState.address
        val sourceAddress = oldGameState.address
        val sourceStack = mutableStacks.find { it.address == sourceAddress } ?: return mutableStacks
        if (sourceStack.topCard != cardToMove) return mutableStacks

        val updatedStacks = mutableStacks.map { stack ->
            when (stack.address) {
                sourceAddress -> stack.copy(cards = stack.cards.dropLast(1))
                targetAddress -> {
                    val targetStack = mutableStacks.find { it.address == targetAddress }
                    targetStack?.copy(cards = targetStack.cards + cardToMove) ?: stack
                }

                else -> stack
            }
        }.toMutableList()

        if (updatedStacks.none { it.address == targetAddress }) {
            updatedStacks.add(CardStack(cards = listOf(cardToMove), address = targetAddress))
        }

        mutableStacks.clear()
        mutableStacks.addAll(updatedStacks)
        return mutableStacks
    }

    private fun validationCardOnFoundation(
        oldCard: Card?,
        newCard: Card?,
        suitFoundations: List<Suit>,
    ): Boolean {
        if (oldCard == null) return false
        return if (newCard == null) {
            oldCard.rank == Rank.KING && !suitFoundations.contains(oldCard.suit)
        } else {
            oldCard.suit == newCard.suit
                    && (oldCard.rank.value == (newCard.rank.value - 1)
                    || (oldCard.rank == Rank.KING && newCard.rank == Rank.ACE))
        }
    }

    private fun validationCardOnExternal(
        oldState: GameState,
        newState: GameState,
    ): Boolean {
        return if (newState.card == null) {
            oldState.address.type == SlotType.INNER_WELL && oldState.address.index == newState.address.index
        } else {
            oldState.card?.suit == newState.card.suit
                    && (oldState.card.rank.value == (newState.card.rank.value.plus(1))
                    || (oldState.card.rank == Rank.ACE && newState.card.rank == Rank.KING))
        }
    }

    private fun validationCardOnWaste(
        oldCard: Card?,
        newCard: Card?,
    ): Boolean {
        if (oldCard == null) return false
        return if (newCard == null) {
            oldCard.rank == Rank.ACE
        } else {
            oldCard.suit == newCard.suit
                    && (oldCard.rank.value == (newCard.rank.value.plus(1))
                    || (oldCard.rank == Rank.ACE && newCard.rank == Rank.KING))
        }
    }

    private fun dealNewCardsOnTop(
        stock: CardStack,
        stockPlayStacks: List<CardStack>,
        currentStacks: MutableList<CardStack>,
        dealCount: Int,
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
        currentDealCount: Int,
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
        newCards: List<Card>,
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

    private fun createInnerWells(deck: MutableList<Card>): List<CardStack> {
        val innerWells = List(4) { index ->
            CardStack(
                cards = deck.takeFromDeck(10).map { it.copy(isFaceUp = true) },
                address = SlotAddress(SlotType.INNER_WELL, index)
            )
        }
        return innerWells
    }

    private fun createExternalWells(deck: MutableList<Card>): List<CardStack> {
        return List(4) { index ->
            val topCard = deck.removeFirst().copy(isFaceUp = true)
            CardStack(
                cards = listOf(topCard),
                address = SlotAddress(SlotType.EXTERNAL_WELL, index)
            )
        }
    }

}