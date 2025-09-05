package logic

import debugLog
import models.WellCardStack
import model.CardState
import model.GameState
import model.MoveCardResult
import models.WellSlotAddress
import models.WellSlotType
import model.StockClickResult
import models.Card
import models.Rank
import models.Suit

class GameSetupFactoryImpl : GameSetupFactory {

    override fun createNewGame(): List<WellCardStack> {
        val deck = generateDoubleDeck().shuffled().toMutableList()
        val innerWells = createInnerWells(deck)
        val externalWells = createExternalWells(deck)
        val stock = listOf(WellCardStack(deck, WellSlotAddress(WellSlotType.STOCK)))
        return innerWells + externalWells + stock
    }

    override fun handleStockClick(
        currentStacks: List<WellCardStack>,
        currentDealCount: Int,
    ): StockClickResult {
        val mutableStacks = currentStacks.toMutableList()

        val stockStack = mutableStacks.find { it.address.type == WellSlotType.STOCK }
        val stockPlayStacks = mutableStacks.filter { it.address.type == WellSlotType.STOCK_PLAY }
        debugLog("deal count: $currentDealCount, play size: ${stockPlayStacks.size}, top card ${stockPlayStacks.map { it.topCard }}, card size${stockPlayStacks.map { it.cards.size }}, card adress ${stockPlayStacks.map { it.address }}")
        return if (stockStack != null && stockStack.cards.isNotEmpty()) {
            val updatedStacks =
                dealNewCardsOnTop(stockStack, stockPlayStacks, mutableStacks, currentDealCount)
            StockClickResult(updatedStacks, currentDealCount)
        } else {
            resetStockToDeck(stockPlayStacks, mutableStacks, currentDealCount)
        }
    }

    override fun handleMoveCard(
        currentStacks: List<WellCardStack>,
        oldGameState: GameState,
        currentGameState: GameState,
    ): MoveCardResult {
        return when (currentGameState.state) {
            CardState.DEFAULT -> {
                if (currentGameState.state == oldGameState.state) {
                    handleEmptySlotClicked(currentGameState)
                } else {
                    handleValidMove(currentStacks.toMutableList(), oldGameState, currentGameState)
                }
            }
            CardState.HINTED -> {
                handleEmptySlotClicked(currentGameState)
            }
            CardState.SELECTED,
            CardState.SUCCESS,
            CardState.ERROR -> MoveCardResult.Default
        }
    }

    override fun handleHint(currentStacks: List<WellCardStack>): List<GameState> {
        val helpCards = mutableListOf<GameState>()
        val foundations = currentStacks.filter { it.address.type == WellSlotType.FOUNDATION }
        val foundationSuits = foundations.mapNotNull { it.topCard?.suit }
        val emptyFoundationAddresses = (0..3)
            .map { WellSlotAddress(WellSlotType.FOUNDATION, it) }
            .filter { address -> foundations.none { it.address.index == address.index } }
        val wasteExists = currentStacks.any { it.address.type == WellSlotType.WASTE }
        currentStacks.forEach { sourceStack ->
            val topCard = sourceStack.topCard ?: return@forEach

            when (sourceStack.address.type) {
                WellSlotType.FOUNDATION, WellSlotType.STOCK -> Unit

                WellSlotType.INNER_WELL, WellSlotType.EXTERNAL_WELL, WellSlotType.STOCK_PLAY -> {
                    checkFoundationMoves(
                        sourceStack,
                        topCard,
                        foundations,
                        emptyFoundationAddresses,
                        foundationSuits,
                        helpCards
                    )
                    if (!wasteExists && validationCardOnWaste(topCard, null)) {
                        helpCards.addSelectedPair(
                            topCard,
                            sourceStack.address,
                            null,
                            WellSlotAddress(WellSlotType.WASTE)
                        )
                    }
                    checkOtherMoves(sourceStack, topCard, currentStacks, helpCards)
                }

                WellSlotType.WASTE -> {
                    checkFoundationMoves(
                        sourceStack,
                        topCard,
                        foundations,
                        emptyFoundationAddresses,
                        foundationSuits,
                        helpCards
                    )
                    checkOtherMoves(sourceStack, topCard, currentStacks, helpCards)
                }
            }
        }

        return helpCards
    }

    private fun checkFoundationMoves(
        sourceStack: WellCardStack,
        card: Card,
        foundations: List<WellCardStack>,
        emptyFoundations: List<WellSlotAddress>,
        foundationSuits: List<Suit>,
        result: MutableList<GameState>,
    ) {

        emptyFoundations.forEach { emptyAddress ->
            if (validationCardOnFoundation(card, null, foundationSuits)) {
                result.addSelectedPair(card, sourceStack.address, null, emptyAddress)
            }
        }

        foundations.forEach { foundation ->
            foundation.topCard?.let { foundationCard ->
                if (validationCardOnFoundation(card, foundationCard, foundationSuits)) {
                    result.addSelectedPair(
                        card,
                        sourceStack.address,
                        foundationCard,
                        foundation.address
                    )
                }
            }
        }
    }

    private fun checkOtherMoves(
        sourceStack: WellCardStack,
        card: Card,
        allStacks: List<WellCardStack>,
        result: MutableList<GameState>,
    ) {
        allStacks.forEach { targetStack ->
            if (sourceStack.address == targetStack.address) return@forEach

            when (targetStack.address.type) {
                WellSlotType.WASTE -> {
                    if (validationCardOnWaste(card, targetStack.topCard)) {
                        result.addSelectedPair(
                            card,
                            sourceStack.address,
                            targetStack.topCard,
                            targetStack.address
                        )
                    }
                }

                WellSlotType.EXTERNAL_WELL -> {
                    if (validationCardOnExternal(
                            GameState(card, sourceStack.address, CardState.DEFAULT),
                            GameState(targetStack.topCard, targetStack.address, CardState.DEFAULT)
                        )
                    ) {
                        result.addSelectedPair(
                            card,
                            sourceStack.address,
                            targetStack.topCard,
                            targetStack.address
                        )
                    }
                }

                WellSlotType.STOCK,
                WellSlotType.STOCK_PLAY,
                WellSlotType.INNER_WELL,
                WellSlotType.FOUNDATION -> Unit
            }
        }
    }

    private fun MutableList<GameState>.addSelectedPair(
        sourceCard: Card?,
        sourceAddress: WellSlotAddress,
        targetCard: Card?,
        targetAddress: WellSlotAddress,
    ) {
        this += GameState(card = sourceCard, address = sourceAddress, state = CardState.HINTED)
        this += GameState(card = targetCard, address = targetAddress, state = CardState.HINTED)
    }


    private fun handleEmptySlotClicked(currentGameState: GameState) : MoveCardResult {
       return if (currentGameState.card != null
            && currentGameState.address.type !in setOf(
                WellSlotType.STOCK,
                WellSlotType.FOUNDATION
            )
        )
            MoveCardResult.Selected else MoveCardResult.Default
    }

    private fun handleValidMove(
        currentDeck: MutableList<WellCardStack>,
        oldGameState: GameState,
        gameState: GameState,
    ): MoveCardResult {
        return when (gameState.address.type) {
            WellSlotType.FOUNDATION -> {
                val foundSuit = currentDeck
                    .filter { it.address.type == WellSlotType.FOUNDATION }
                    .mapNotNull { stack -> stack.cards.firstOrNull()?.suit }
                val valid =
                    validationCardOnFoundation(oldGameState.card, gameState.card, foundSuit)
                handleCardMove(valid, currentDeck, oldGameState, gameState)
            }

            WellSlotType.WASTE -> {
                val valid = validationCardOnWaste(oldGameState.card, gameState.card)
                handleCardMove(valid, currentDeck, oldGameState, gameState)
            }

            WellSlotType.EXTERNAL_WELL -> {
                val valid = validationCardOnExternal(oldGameState, gameState)
                handleCardMove(valid, currentDeck, oldGameState, gameState)
            }

            WellSlotType.INNER_WELL,
            WellSlotType.STOCK_PLAY,
                -> MoveCardResult.Error

            WellSlotType.STOCK -> MoveCardResult.Default
        }
    }

    private fun handleCardMove(
        isValid: Boolean,
        currentDeck: MutableList<WellCardStack>,
        oldGameState: GameState,
        gameState: GameState,
    ): MoveCardResult {
        return if (isValid) {
            val newDeck = moveCardToNewAddress(currentDeck, oldGameState, gameState)
            MoveCardResult.Success(newDeck)
        } else MoveCardResult.Error
    }

    fun moveCardToNewAddress(
        mutableStacks: MutableList<WellCardStack>,
        oldGameState: GameState,
        gameState: GameState,
    ): List<WellCardStack> {

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
            updatedStacks.add(WellCardStack(cards = listOf(cardToMove), address = targetAddress))
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
            oldState.address.type == WellSlotType.INNER_WELL && oldState.address.index == newState.address.index
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
        stock: WellCardStack,
        stockPlayStacks: List<WellCardStack>,
        currentStacks: MutableList<WellCardStack>,
        dealCount: Int,
    ): List<WellCardStack> {
        val cardsToDeal = stock.cards.take(dealCount).map { it.copy(isFaceUp = true) }
        val remainingCardsInStock = stock.cards.drop(dealCount)

        val updatedStock = stock.copy(cards = remainingCardsInStock)
        val updatedStockPlay = updateStockPlayStacks(stockPlayStacks, cardsToDeal)

        return currentStacks.apply {
            removeAll { it.address.type == WellSlotType.STOCK || it.address.type == WellSlotType.STOCK_PLAY }
            add(updatedStock)
            addAll(updatedStockPlay)
        }
    }

    private fun resetStockToDeck(
        stockPlayStacks: List<WellCardStack>,
        currentStacks: MutableList<WellCardStack>,
        currentDealCount: Int,
    ): StockClickResult {
        val maxCardsInStack = stockPlayStacks.maxOf { it.cards.size }
        val allCardsFromStockPlay = mutableListOf<Card>()

        for (level in 0 until maxCardsInStack) {
            for (stack in stockPlayStacks) {
                if (level < stack.cards.size) {
                    allCardsFromStockPlay.add(stack.cards[level])
                }
            }
        }
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
        val newStock = WellCardStack(cards = resetCards, address = WellSlotAddress(WellSlotType.STOCK))

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
            WellCardStack(cards = listOf(card), address = WellSlotAddress(WellSlotType.STOCK_PLAY, index))
        }

        // Обновляем общий список стопок
        val updatedStacks = currentStacks.apply {
            removeAll { it.address.type == WellSlotType.STOCK || it.address.type == WellSlotType.STOCK_PLAY }
            if (finalStock != null) {
                add(finalStock) // Добавляем колоду если там есть карты
            }
            addAll(updatedStockPlay) // Добавляем выложенные карты
        }

        return StockClickResult(updatedStacks, newDealCount)
    }

    private fun updateStockPlayStacks(
        existingStacks: List<WellCardStack>,
        newCards: List<Card>,
    ): List<WellCardStack> {
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
                WellCardStack(cards = listOf(card), address = WellSlotAddress(WellSlotType.STOCK_PLAY, index))
            }
        }
    }

    private fun createInnerWells(deck: MutableList<Card>): List<WellCardStack> {
        val innerWells = List(4) { index ->
            WellCardStack(
                cards = deck.takeFromDeck(10).map { it.copy(isFaceUp = true) },
                address = WellSlotAddress(WellSlotType.INNER_WELL, index)
            )
        }
        return innerWells
    }

    private fun createExternalWells(deck: MutableList<Card>): List<WellCardStack> {
        return List(4) { index ->
            val topCard = deck.removeAt(0).copy(isFaceUp = true)
            WellCardStack(
                cards = listOf(topCard),
                address = WellSlotAddress(WellSlotType.EXTERNAL_WELL, index)
            )
        }
    }

}