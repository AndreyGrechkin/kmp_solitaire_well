package com.defey.solitairewell.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.defey.solitairewell.factories.CardResourcesFactory
import com.defey.solitairewell.model.GameState
import models.Deck
import models.WellCardStack
import models.WellSlotAddress
import models.WellSlotType

@Composable
fun ActiveCardsStackRow(
    stackWells: List<WellCardStack>,
    cardFactory: CardResourcesFactory,
    modifier: Modifier,
    gameState: GameState,
    backCardIndex: Int,
    deck: Deck,
    helpState: List<GameState>,
    onAnimationComplete: () -> Unit,
    onClick: (GameState) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(5) { index ->
            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(WellSlotType.STOCK_PLAY, index),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = helpState,
                backCardIndex = backCardIndex,
                deck = deck,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )
        }
    }
}