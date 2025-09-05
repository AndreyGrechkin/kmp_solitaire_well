package repository

import kotlinx.coroutines.flow.Flow
import models.Deck
import models.WellCardStack
import models.WellGameState

interface WellRepository {

    suspend fun setWellCards(stacks: List<WellCardStack>)
    suspend fun getWellCards(): List<WellCardStack>
    suspend fun deleteWellCards()

    suspend fun setWellGameState(state: WellGameState)
    suspend fun getWellGameState(): WellGameState
    suspend fun deleteWellGameState()
}