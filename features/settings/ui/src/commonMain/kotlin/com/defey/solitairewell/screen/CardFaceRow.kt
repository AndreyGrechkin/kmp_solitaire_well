package com.defey.solitairewell.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.defey.solitairewell.factories.CardResourcesFactory
import com.defey.solitairewell.models.Card
import com.defey.solitairewell.models.Deck
import com.defey.solitairewell.models.Rank
import com.defey.solitairewell.models.Suit

@Composable
fun CardFaceRow(
    cardFactory: CardResourcesFactory,
    deck: Deck,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = horizontalArrangement
    ) {
        CardRankSlot(
            cardResource = cardFactory.createCardResources(
                Card(
                    suit = Suit.DIAMONDS,
                    rank = Rank.TEN
                ), deck
            ),
            isFaceUp = true
        )
        CardRankSlot(
            cardResource = cardFactory.createCardResources(
                Card(
                    suit = Suit.CLUBS,
                    rank = Rank.JACK
                ), deck
            ),
            isFaceUp = true
        )
        CardRankSlot(
            cardResource = cardFactory.createCardResources(
                Card(
                    suit = Suit.HEARTS,
                    rank = Rank.QUEEN
                ), deck
            ),
            isFaceUp = true
        )
        CardRankSlot(
            cardResource = cardFactory.createCardResources(
                Card(
                    suit = Suit.SPADES,
                    rank = Rank.KING
                ), deck
            ),
            isFaceUp = true
        )
    }
}