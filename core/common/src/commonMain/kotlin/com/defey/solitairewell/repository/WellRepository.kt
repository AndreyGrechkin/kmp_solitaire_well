package com.defey.solitairewell.repository

import com.defey.solitairewell.models.WellCardStack
import com.defey.solitairewell.models.WellGameState

interface WellRepository {
    suspend fun setWellCards(stacks: List<WellCardStack>)
    suspend fun getWellCards(): List<WellCardStack>
    suspend fun deleteWellCards()
    suspend fun setWellGameState(state: WellGameState)
    suspend fun getWellGameState(): WellGameState
    suspend fun deleteWellGameState()
}