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
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.well_game_step
import com.defey.solitairewell.resources.well_waste
import com.defey.solitairewell.factories.CardResourcesFactory
import com.defey.solitairewell.model.GameState
import models.Deck
import models.WellCardStack
import models.WellSlotAddress
import models.WellSlotType
import org.jetbrains.compose.resources.stringResource
import com.defey.solitairewell.theme.CardColors

@Composable
fun CardsStackRow(
    stackWells: List<WellCardStack>,
    gameState: GameState,
    helpState: List<GameState>,
    step: Int,
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
        val stepMessage = stringResource(Res.string.well_game_step)
        Column {
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
                text = "$stepMessage $step",
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
                text = stringResource(Res.string.well_waste),
                color = CardColors.black,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}