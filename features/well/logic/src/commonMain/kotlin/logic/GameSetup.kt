package logic

import model.GameState

fun initGame(): GameState {
    val deck = generateDoubleDeck().toMutableList()
    val wells = List(4) { listOf(deck.removeLast().copy(isFaceUp = true)) }
    return GameState(
        stock = deck,
        wells = wells,
        foundations = List(4) { emptyList() }
    )
}