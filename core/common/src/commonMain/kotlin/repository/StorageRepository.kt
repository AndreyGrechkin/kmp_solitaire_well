package repository

import KeyValueStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import models.Deck


interface StorageRepository{
    suspend fun setDeck(deck: Deck)
    suspend fun getDeck(): Deck?
    fun getDeckFlow(): Flow<Deck?>

    suspend fun setBackCard(index: Int)
    suspend fun getBackCard(): Int
    fun getBackCardFlow(): Flow<Int>
}

// Пример использования для хранения одного параметра
class StorageRepositoryImpl(private val storage: KeyValueStorage): StorageRepository {
    companion object {
        private const val DECK_KEY = "deck"
        private const val BACK_CARD_KEY = "back_card_key"
        private const val DEAL_COUNT_KEY = "deal_count_key"
    }

    override suspend fun setDeck(deck: Deck) {
        storage.putString(DECK_KEY, deck.name)
    }

    override suspend fun getDeck(): Deck? {
        return storage.getString(DECK_KEY)?.let { deckName ->
            Deck.entries.find { it.name == deckName }
        }
    }

    override fun getDeckFlow(): Flow<Deck?> {
        return storage.getStringFlow(DECK_KEY).map { deckName ->
            deckName?.let { Deck.entries.find { it.name == deckName } }
        }
    }

    override suspend fun setBackCard(index: Int) {
        storage.putString(BACK_CARD_KEY, index.toString())
    }

    override suspend fun getBackCard(): Int {
        return storage.getString(BACK_CARD_KEY)?.toInt() ?: 0
    }

    override fun getBackCardFlow(): Flow<Int> {
        return storage.getStringFlow(BACK_CARD_KEY).map { value ->
            value?.toInt() ?: 0
        }
    }
}