package repository

import kotlinx.coroutines.flow.Flow
import models.Deck

interface StorageRepository {
    suspend fun setDeck(deck: Deck)
    suspend fun getDeck(): Deck?
    fun getDeckFlow(): Flow<Deck?>

    suspend fun setBackCard(index: Int)
    suspend fun getBackCard(): Int
    fun getBackCardFlow(): Flow<Int>
}