package com.defey.solitairewell.repository

import com.defey.solitairewell.KeyValueStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import models.Deck
import repository.StorageRepository

class StorageRepositoryImpl(private val storage: KeyValueStorage) : StorageRepository {

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

    override suspend fun setBackgroundIndex(index: Int) {
        storage.putString(BACKGROUND_INDEX_KEY, index.toString())
    }

    override suspend fun getBackgroundIndex(): Int {
        return storage.getString(BACKGROUND_INDEX_KEY)?.toInt() ?: 0
    }

    override fun getBackgroundIndexFlow(): Flow<Int> {
        return storage.getStringFlow(BACKGROUND_INDEX_KEY).map { value ->
            value?.toInt() ?: 0
        }
    }

    override fun setLanguage(iso: String) {
        storage.setString(LANGUAGE_KEY, iso)
    }

    override fun getLanguage(): String {
        return storage.getStringTab(LANGUAGE_KEY) ?: ""
    }

    companion object {
        private const val DECK_KEY = "deck"
        private const val BACK_CARD_KEY = "back_card_key"
        private const val LANGUAGE_KEY = "language_key"
        private const val BACKGROUND_INDEX_KEY = "background_index_key"
    }
}