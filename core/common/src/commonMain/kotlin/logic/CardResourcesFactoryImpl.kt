package logic

import com.defey.solitairewell.data.resources.JC
import com.defey.solitairewell.data.resources.JD
import com.defey.solitairewell.data.resources.JH
import com.defey.solitairewell.data.resources.JS
import com.defey.solitairewell.data.resources.KC
import com.defey.solitairewell.data.resources.KD
import com.defey.solitairewell.data.resources.KH
import com.defey.solitairewell.data.resources.KS
import com.defey.solitairewell.data.resources.QC
import com.defey.solitairewell.data.resources.QD
import com.defey.solitairewell.data.resources.QH
import com.defey.solitairewell.data.resources.QS
import com.defey.solitairewell.data.resources.Res
import com.defey.solitairewell.data.resources.club
import com.defey.solitairewell.data.resources.diamond
import com.defey.solitairewell.data.resources.heart
import com.defey.solitairewell.data.resources.spade
import models.Card
import models.CardResource
import models.Rank
import models.Suit
import org.jetbrains.compose.resources.DrawableResource

class CardResourcesFactoryImpl : CardResourcesFactory {
    override fun createCardResources(card: Card): CardResource {
        return when (card.rank) {
            Rank.ACE -> handleLowRank("A", card.suit)
            Rank.TWO -> handleLowRank("2", card.suit)
            Rank.THREE -> handleLowRank("3", card.suit)
            Rank.FOUR -> handleLowRank("4", card.suit)
            Rank.FIVE -> handleLowRank("5", card.suit)
            Rank.SIX -> handleLowRank("6", card.suit)
            Rank.SEVEN -> handleLowRank("7", card.suit)
            Rank.EIGHT -> handleLowRank("8", card.suit)
            Rank.NINE -> handleLowRank("9", card.suit)
            Rank.TEN -> handleLowRank("10", card.suit)
            Rank.JACK -> {
                CardResource(
                    rank = "J",
                    suit = handleSuit(card.suit),
                    image = when (card.suit) {
                        Suit.HEARTS -> Res.drawable.JH
                        Suit.DIAMONDS -> Res.drawable.JD
                        Suit.CLUBS -> Res.drawable.JC
                        Suit.SPADES -> Res.drawable.JS
                    }
                )
            }

            Rank.QUEEN -> {
                CardResource(
                    rank = "Q",
                    suit = handleSuit(card.suit),
                    image = when (card.suit) {
                        Suit.HEARTS -> Res.drawable.QH
                        Suit.DIAMONDS -> Res.drawable.QD
                        Suit.CLUBS -> Res.drawable.QC
                        Suit.SPADES -> Res.drawable.QS
                    }
                )
            }

            Rank.KING -> {
                CardResource(
                    rank = "K",
                    suit = handleSuit(card.suit),
                    image = when (card.suit) {
                        Suit.HEARTS -> Res.drawable.KH
                        Suit.DIAMONDS -> Res.drawable.KD
                        Suit.CLUBS -> Res.drawable.KC
                        Suit.SPADES -> Res.drawable.KS
                    }
                )
            }
        }
    }

    private fun handleSuit(suit: Suit): DrawableResource {
        return when (suit) {
            Suit.HEARTS -> Res.drawable.heart
            Suit.DIAMONDS -> Res.drawable.diamond
            Suit.CLUBS -> Res.drawable.club
            Suit.SPADES -> Res.drawable.spade
        }
    }

    private fun handleLowRank(rank: String, suit: Suit): CardResource {
        val suitRes = handleSuit(suit)
        return CardResource(
            rank = rank,
            suit = suitRes,
            image = suitRes
        )
    }
}