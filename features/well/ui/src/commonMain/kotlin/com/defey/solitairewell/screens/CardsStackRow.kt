package com.defey.solitairewell.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import factories.CardResourcesFactory
import model.GameState
import models.Deck
import models.WellCardStack
import models.WellSlotAddress
import models.WellSlotType
import theme.CardColors

@Composable
fun CardsStackRow(
    stackWells: List<WellCardStack>,
    gameState: GameState,
    helpState: List<GameState>,
    message: String,
    deck: Deck,
    backCardIndex: Int,
    cardFactory: CardResourcesFactory,
    onAnimationComplete: () -> Unit,
    onClick: (GameState) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Колода (слева)
            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(WellSlotType.STOCK),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = emptyList(),
                deck = deck,
                backCardIndex = backCardIndex,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )
            Text(
                text = message,
                color = CardColors.black,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.weight(2f))

        Column {
            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(WellSlotType.WASTE),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = helpState,
                backCardIndex = backCardIndex,
                deck = deck,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )
            Text(
                text = "СКЛАД",
                color = CardColors.black,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}