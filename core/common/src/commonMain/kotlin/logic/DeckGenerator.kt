package logic

import models.Card
import models.Rank
import models.Suit

fun generateDoubleDeck(): List<Card> {
    return (0..1).flatMap { deckId ->
        Suit.entries.flatMap { suit ->
            Rank.entries.map { rank -> Card(suit, rank) }
        }
    }.shuffled()
}

fun MutableList<Card>.takeFromDeck(count: Int): List<Card> {
    val taken = this.take(count)
    repeat(count) { this.removeFirst() }
    return taken
}