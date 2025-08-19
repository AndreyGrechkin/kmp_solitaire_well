package logic

import model.GameState
import models.Card
import models.Rank

object MoveLogic {
    fun canPlaceOnWell(card: Card, well: List<Card>): Boolean {
        if (well.isEmpty()) return false
        val topCard = well.last()
        return card.suit == topCard.suit && card.rank.value == topCard.rank.value + 1
    }

    fun autoMoveToFoundation(gameState: GameState): GameState {
        // Проверяем, можно ли переместить верхнюю карту колодца в фундамент
        // (например, если это A или следующая по порядку)
        // Возвращаем обновлённое состояние
        TODO()
    }

    // Автоматический перенос тузов в фундамент
    fun autoMoveAces(state: GameState): GameState {
        val newWells = state.wells.map { well ->
            if (well.isNotEmpty() && well.last().rank == Rank.ACE) well.dropLast(1)
            else well
        }
        val newFoundations = state.foundations.map { foundation ->
            if (foundation.isEmpty()) listOf() // Добавляем туза
            else foundation
        }
        return state.copy(wells = newWells, foundations = newFoundations)
    }
}