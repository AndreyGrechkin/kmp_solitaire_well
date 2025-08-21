package model

sealed class MoveCardResult {
    data class Success(val newStacks: List<CardStack>) : MoveCardResult()
    data object Default : MoveCardResult()
    data object Selected : MoveCardResult()
    data object Error : MoveCardResult()
}