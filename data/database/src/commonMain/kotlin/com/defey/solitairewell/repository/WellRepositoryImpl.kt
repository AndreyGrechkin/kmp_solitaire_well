package com.defey.solitairewell.repository

import com.defey.solitairewell.dao.WellCardDao
import com.defey.solitairewell.dao.WellGameStateDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import com.defey.solitairewell.models.Card
import com.defey.solitairewell.models.Rank
import com.defey.solitairewell.models.Suit
import com.defey.solitairewell.models.WellCardStack
import com.defey.solitairewell.models.WellGameState
import com.defey.solitairewell.models.WellSlotAddress
import com.defey.solitairewell.models.WellSlotType
import com.defey.solitairewell.tables.WellCardEntity
import com.defey.solitairewell.tables.WellGameStateEntity

class WellRepositoryImpl(
    private val wellCardDao: WellCardDao,
    private val wellGameStateDao: WellGameStateDao,
) : WellRepository {

    override suspend fun setWellCards(stacks: List<WellCardStack>) {
        withContext(Dispatchers.IO) {
            wellCardDao.deleteWellCards()
            val allCards = stacks.flatMap { stack ->
                if (stack.cards.isEmpty()) {
                    listOf(
                        WellCardEntity(
                            stackType = stack.address.type.name,
                            stackIndex = stack.address.index,
                            suit = null,
                            rank = null,
                            isFaceUp = false,
                            position = 0,
                        )
                    )
                } else {
                    stack.cards.mapIndexed { position, card ->
                        WellCardEntity(
                            stackType = stack.address.type.name,
                            stackIndex = stack.address.index,
                            suit = card.suit.name,
                            rank = card.rank.value,
                            isFaceUp = card.isFaceUp,
                            position = position
                        )
                    }
                }
            }
            wellCardDao.setWellCards(allCards)
        }
    }

    override suspend fun getWellCards(): List<WellCardStack> = withContext(Dispatchers.IO) {
        val allCards = wellCardDao.getWellCards()

        val grouped = allCards.groupBy { it.stackType to it.stackIndex }

        grouped.map { (key, cardEntities) ->
            val (stackType, stackIndex) = key
            val cards = cardEntities
                .sortedBy { it.position }
                .mapNotNull { cardEntity ->
                    if (cardEntity.suit == null || cardEntity.rank == null) return@mapNotNull null
                    Card(
                        suit = Suit.valueOf(cardEntity.suit),
                        rank = Rank.entries.first { it.value == cardEntity.rank },
                        isFaceUp = cardEntity.isFaceUp
                    )
                }
            WellCardStack(
                cards = cards,
                address = WellSlotAddress(
                    type = WellSlotType.valueOf(stackType),
                    index = stackIndex
                )
            )
        }
    }


    override suspend fun deleteWellCards() {
        withContext(Dispatchers.IO) {
            wellCardDao.deleteWellCards()
        }
    }

    override suspend fun setWellGameState(state: WellGameState) = withContext(Dispatchers.IO) {
        wellGameStateDao.setWellGameState(
            WellGameStateEntity(
                countGameStack = state.countGameStack,
                step = state.step
            )
        )
    }

    override suspend fun getWellGameState(): WellGameState = withContext(Dispatchers.IO) {
        val state = wellGameStateDao.getWellGameState()
        return@withContext WellGameState(
            countGameStack = state?.countGameStack,
            step = state?.step
        )
    }

    override suspend fun deleteWellGameState() = withContext(Dispatchers.IO) {
        wellGameStateDao.deleteWellGameState()
    }
}