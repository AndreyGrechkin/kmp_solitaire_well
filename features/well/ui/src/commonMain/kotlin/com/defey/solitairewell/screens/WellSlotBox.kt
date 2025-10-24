package com.defey.solitairewell.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.calculateHalfCardHeight
import com.defey.solitairewell.WellViewModel
import com.defey.solitairewell.factories.CardResourcesFactory
import com.defey.solitairewell.model.GameState
import models.Deck
import models.WellCardStack
import models.WellSlotAddress
import models.WellSlotType
import com.defey.solitairewell.rememberCardSize

@Composable
fun WellSlotBox(
    stackWells: List<WellCardStack>,
    backCardIndex: Int,
    gameState: GameState,
    helpState: List<GameState>,
    cardFactory: CardResourcesFactory,
    deck: Deck,
    onAnimationComplete: () -> Unit,
    onClick: (GameState) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 32.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(
                    WellSlotType.EXTERNAL_WELL,
                    WellViewModel.Companion.TOP_INDEX
                ),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = helpState,
                backCardIndex = backCardIndex,
                deck = deck,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(
                    WellSlotType.INNER_WELL,
                    WellViewModel.Companion.TOP_INDEX
                ),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = helpState,
                backCardIndex = backCardIndex,
                deck = deck,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(
                    WellSlotType.INNER_WELL,
                    WellViewModel.Companion.BOTTOM_INDEX
                ),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = helpState,
                backCardIndex = backCardIndex,
                deck = deck,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(
                    WellSlotType.EXTERNAL_WELL,
                    WellViewModel.Companion.BOTTOM_INDEX
                ),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = helpState,
                backCardIndex = backCardIndex,
                deck = deck,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )

        }
        Column(
            modifier = Modifier
                .padding(top = calculateHalfCardHeight()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(
                        WellSlotType.FOUNDATION,
                        WellViewModel.Companion.LEFT_INDEX
                    ),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    helpState = helpState,
                    backCardIndex = backCardIndex,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                )
                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(
                        WellSlotType.FOUNDATION,
                        WellViewModel.Companion.TOP_INDEX
                    ),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    helpState = helpState,
                    backCardIndex = backCardIndex,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )
            }
            Row {
                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(
                        WellSlotType.EXTERNAL_WELL,
                        WellViewModel.Companion.LEFT_INDEX
                    ),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    backCardIndex = backCardIndex,
                    helpState = helpState,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )

                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(
                        WellSlotType.INNER_WELL,
                        WellViewModel.Companion.LEFT_INDEX
                    ),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    helpState = helpState,
                    backCardIndex = backCardIndex,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )

                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                )
                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(
                        WellSlotType.INNER_WELL,
                        WellViewModel.Companion.RIGHT_INDEX
                    ),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    helpState = helpState,
                    backCardIndex = backCardIndex,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )

                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(
                        WellSlotType.EXTERNAL_WELL,
                        WellViewModel.Companion.RIGHT_INDEX
                    ),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    helpState = helpState,
                    backCardIndex = backCardIndex,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )

            }
            Row {
                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(
                        WellSlotType.FOUNDATION,
                        WellViewModel.Companion.BOTTOM_INDEX
                    ),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    helpState = helpState,
                    backCardIndex = backCardIndex,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                )
                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(
                        WellSlotType.FOUNDATION,
                        WellViewModel.Companion.RIGHT_INDEX
                    ),
                    cardFactory = cardFactory,
                    backCardIndex = backCardIndex,
                    gameState = gameState,
                    helpState = helpState,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )
            }
        }
    }
}