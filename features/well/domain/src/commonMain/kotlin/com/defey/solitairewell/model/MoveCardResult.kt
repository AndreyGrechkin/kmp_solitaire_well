package com.defey.solitairewell.model

import com.defey.solitairewell.models.WellCardStack

sealed class MoveCardResult {
    data class Success(val newStacks: List<WellCardStack>) : MoveCardResult()
    data object Default : MoveCardResult()
    data object Selected : MoveCardResult()
    data object Error : MoveCardResult()
}