package com.defey.solitairewell.logic

import com.defey.solitairewell.models.Card
import com.defey.solitairewell.models.Rank
import com.defey.solitairewell.models.Suit

fun generateDoubleDeck(): List<Card> {
    return (0..1).flatMap { deckId ->
        Suit.entries.flatMap { suit ->
            Rank.entries.map { rank -> Card(suit, rank) }
        }
    }.shuffled()
}

fun MutableList<Card>.takeFromDeck(count: Int): List<Card> {
    val actualCount = minOf(count, this.size)
    val taken = this.take(actualCount)
    repeat(actualCount) { this.removeAt(0) }
    return taken
}