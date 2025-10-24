package com.defey.solitairewell.screens

import androidx.compose.runtime.Composable
import com.defey.solitairewell.factories.CardResourcesFactory
import com.defey.solitairewell.model.CardState
import com.defey.solitairewell.model.GameState
import models.Deck
import models.WellCardStack
import models.WellSlotAddress
import models.WellSlotType

@Composable
fun CreateCardSlot(
    stackList: List<WellCardStack>,
    address: WellSlotAddress,
    backCardIndex: Int,
    cardFactory: CardResourcesFactory,
    gameState: GameState,
    helpState: List<GameState>,
    deck: Deck,
    onAnimationComplete: () -> Unit,
    onClick: (GameState) -> Unit,
) {
    val stack = stackList.find { it.address == address }

    val state = if (helpState.isEmpty()) {
        if (gameState.address == address) gameState.state
        else CardState.DEFAULT
    } else {
        helpState.find { it.address == address }?.state ?: CardState.DEFAULT
    }
    val stackSize = stack?.cards?.size ?: 0

    val isVisibleCount = when (address.type) {
        WellSlotType.FOUNDATION -> true
        WellSlotType.STOCK -> false
        WellSlotType.STOCK_PLAY -> false
        WellSlotType.WASTE -> false
        WellSlotType.INNER_WELL -> true
        WellSlotType.EXTERNAL_WELL -> true
    }

    val backCard = cardFactory.createBackCard(backCardIndex)
    if (stack?.cards.isNullOrEmpty()) {
        EmptyCardSlot(
            state = state,
            stackSize = stackSize,
            onAnimationComplete = onAnimationComplete,
            isVisibleCount = isVisibleCount,
            onClick = {
                onClick(
                    GameState(
                        card = null,
                        address = address,
                        state = state
                    )
                )
            }
        )
    } else {
        val topCard = stack.topCard ?: return
        PlayingCard(
            cardResource = cardFactory.createCardResources(topCard, deck),
            backCard = backCard,
            isFaceUp = topCard.isFaceUp,
            state = state,
            stackSize = stackSize,
            isVisibleCount = isVisibleCount,
            onAnimationComplete = onAnimationComplete,
            onClick = {
                onClick(
                    GameState(
                        card = topCard,
                        address = address,
                        state = state
                    )
                )
            }
        )
    }
}