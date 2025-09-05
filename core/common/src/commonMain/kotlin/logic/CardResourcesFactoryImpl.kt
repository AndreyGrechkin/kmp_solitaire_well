package logic

import com.defey.solitairewell.data.resources.BC_1
import com.defey.solitairewell.data.resources.BC_10
import com.defey.solitairewell.data.resources.BC_11
import com.defey.solitairewell.data.resources.BC_12
import com.defey.solitairewell.data.resources.BC_13
import com.defey.solitairewell.data.resources.BC_14
import com.defey.solitairewell.data.resources.BC_15
import com.defey.solitairewell.data.resources.BC_16
import com.defey.solitairewell.data.resources.BC_2
import com.defey.solitairewell.data.resources.BC_3
import com.defey.solitairewell.data.resources.BC_4
import com.defey.solitairewell.data.resources.BC_5
import com.defey.solitairewell.data.resources.BC_6
import com.defey.solitairewell.data.resources.BC_7
import com.defey.solitairewell.data.resources.BC_8
import com.defey.solitairewell.data.resources.BC_9
import com.defey.solitairewell.data.resources.JC
import com.defey.solitairewell.data.resources.JC_2
import com.defey.solitairewell.data.resources.JC_3
import com.defey.solitairewell.data.resources.JC_4
import com.defey.solitairewell.data.resources.JC_5
import com.defey.solitairewell.data.resources.JC_6
import com.defey.solitairewell.data.resources.JC_7
import com.defey.solitairewell.data.resources.JC_8
import com.defey.solitairewell.data.resources.JD
import com.defey.solitairewell.data.resources.JD_2
import com.defey.solitairewell.data.resources.JD_3
import com.defey.solitairewell.data.resources.JD_4
import com.defey.solitairewell.data.resources.JD_5
import com.defey.solitairewell.data.resources.JD_6
import com.defey.solitairewell.data.resources.JD_7
import com.defey.solitairewell.data.resources.JD_8
import com.defey.solitairewell.data.resources.JH
import com.defey.solitairewell.data.resources.JH_2
import com.defey.solitairewell.data.resources.JH_3
import com.defey.solitairewell.data.resources.JH_4
import com.defey.solitairewell.data.resources.JH_5
import com.defey.solitairewell.data.resources.JH_6
import com.defey.solitairewell.data.resources.JH_7
import com.defey.solitairewell.data.resources.JH_8
import com.defey.solitairewell.data.resources.JS
import com.defey.solitairewell.data.resources.JS_2
import com.defey.solitairewell.data.resources.JS_3
import com.defey.solitairewell.data.resources.JS_4
import com.defey.solitairewell.data.resources.JS_5
import com.defey.solitairewell.data.resources.JS_6
import com.defey.solitairewell.data.resources.JS_7
import com.defey.solitairewell.data.resources.JS_8
import com.defey.solitairewell.data.resources.KC
import com.defey.solitairewell.data.resources.KC_2
import com.defey.solitairewell.data.resources.KC_3
import com.defey.solitairewell.data.resources.KC_4
import com.defey.solitairewell.data.resources.KC_5
import com.defey.solitairewell.data.resources.KC_6
import com.defey.solitairewell.data.resources.KC_7
import com.defey.solitairewell.data.resources.KC_8
import com.defey.solitairewell.data.resources.KD
import com.defey.solitairewell.data.resources.KD_2
import com.defey.solitairewell.data.resources.KD_3
import com.defey.solitairewell.data.resources.KD_4
import com.defey.solitairewell.data.resources.KD_5
import com.defey.solitairewell.data.resources.KD_6
import com.defey.solitairewell.data.resources.KD_7
import com.defey.solitairewell.data.resources.KD_8
import com.defey.solitairewell.data.resources.KH
import com.defey.solitairewell.data.resources.KH_2
import com.defey.solitairewell.data.resources.KH_3
import com.defey.solitairewell.data.resources.KH_4
import com.defey.solitairewell.data.resources.KH_5
import com.defey.solitairewell.data.resources.KH_6
import com.defey.solitairewell.data.resources.KH_7
import com.defey.solitairewell.data.resources.KH_8
import com.defey.solitairewell.data.resources.KS
import com.defey.solitairewell.data.resources.KS_2
import com.defey.solitairewell.data.resources.KS_3
import com.defey.solitairewell.data.resources.KS_4
import com.defey.solitairewell.data.resources.KS_5
import com.defey.solitairewell.data.resources.KS_6
import com.defey.solitairewell.data.resources.KS_7
import com.defey.solitairewell.data.resources.KS_8
import com.defey.solitairewell.data.resources.QC
import com.defey.solitairewell.data.resources.QC_2
import com.defey.solitairewell.data.resources.QC_3
import com.defey.solitairewell.data.resources.QC_4
import com.defey.solitairewell.data.resources.QC_5
import com.defey.solitairewell.data.resources.QC_6
import com.defey.solitairewell.data.resources.QC_7
import com.defey.solitairewell.data.resources.QC_8
import com.defey.solitairewell.data.resources.QD
import com.defey.solitairewell.data.resources.QD_2
import com.defey.solitairewell.data.resources.QD_3
import com.defey.solitairewell.data.resources.QD_4
import com.defey.solitairewell.data.resources.QD_5
import com.defey.solitairewell.data.resources.QD_6
import com.defey.solitairewell.data.resources.QD_7
import com.defey.solitairewell.data.resources.QD_8
import com.defey.solitairewell.data.resources.QH
import com.defey.solitairewell.data.resources.QH_2
import com.defey.solitairewell.data.resources.QH_3
import com.defey.solitairewell.data.resources.QH_4
import com.defey.solitairewell.data.resources.QH_5
import com.defey.solitairewell.data.resources.QH_6
import com.defey.solitairewell.data.resources.QH_7
import com.defey.solitairewell.data.resources.QH_8
import com.defey.solitairewell.data.resources.QS
import com.defey.solitairewell.data.resources.QS_2
import com.defey.solitairewell.data.resources.QS_3
import com.defey.solitairewell.data.resources.QS_4
import com.defey.solitairewell.data.resources.QS_5
import com.defey.solitairewell.data.resources.QS_6
import com.defey.solitairewell.data.resources.QS_7
import com.defey.solitairewell.data.resources.QS_8
import com.defey.solitairewell.data.resources.Res
import com.defey.solitairewell.data.resources.club
import com.defey.solitairewell.data.resources.club_2
import com.defey.solitairewell.data.resources.club_3
import com.defey.solitairewell.data.resources.diamond
import com.defey.solitairewell.data.resources.dimond_2
import com.defey.solitairewell.data.resources.dimond_3
import com.defey.solitairewell.data.resources.heart
import com.defey.solitairewell.data.resources.heart_2
import com.defey.solitairewell.data.resources.heart_3
import com.defey.solitairewell.data.resources.spade
import com.defey.solitairewell.data.resources.spade_2
import com.defey.solitairewell.data.resources.spade_3
import models.Card
import models.CardResource
import models.Deck
import models.Rank
import models.Suit
import org.jetbrains.compose.resources.DrawableResource

class CardResourcesFactoryImpl : CardResourcesFactory {
    override val backCards: List<DrawableResource>
        get() = listOf(
            Res.drawable.BC_1,
            Res.drawable.BC_2,
            Res.drawable.BC_3,
            Res.drawable.BC_4,
            Res.drawable.BC_5,
            Res.drawable.BC_6,
            Res.drawable.BC_7,
            Res.drawable.BC_8,
            Res.drawable.BC_9,
            Res.drawable.BC_10,
            Res.drawable.BC_11,
            Res.drawable.BC_12,
            Res.drawable.BC_13,
            Res.drawable.BC_14,
            Res.drawable.BC_15,
            Res.drawable.BC_16,
        )

    override fun createCardResources(card: Card, deck: Deck): CardResource {
        return when (card.rank) {
            Rank.ACE -> handleLowRank("A", card.suit, deck)
            Rank.TWO -> handleLowRank("2", card.suit, deck)
            Rank.THREE -> handleLowRank("3", card.suit, deck)
            Rank.FOUR -> handleLowRank("4", card.suit, deck)
            Rank.FIVE -> handleLowRank("5", card.suit, deck)
            Rank.SIX -> handleLowRank("6", card.suit, deck)
            Rank.SEVEN -> handleLowRank("7", card.suit, deck)
            Rank.EIGHT -> handleLowRank("8", card.suit, deck)
            Rank.NINE -> handleLowRank("9", card.suit, deck)
            Rank.TEN -> handleLowRank("10", card.suit, deck)
            Rank.JACK -> {
                CardResource(
                    rank = "J",
                    suit = handleSuit(card.suit, deck),
                    image = handleDeckJack(card.suit, deck)
                )
            }

            Rank.QUEEN -> {
                CardResource(
                    rank = "Q",
                    suit = handleSuit(card.suit, deck),
                    image = handleDeckQueen(card.suit, deck)
                )
            }

            Rank.KING -> {
                CardResource(
                    rank = "K",
                    suit = handleSuit(card.suit, deck),
                    image = handleDeckKing(card.suit, deck)
                )
            }
        }
    }

    override fun createBackCard(index: Int): DrawableResource {
        return backCards.getOrNull(index) ?: backCards.first()
    }

    private fun handleSuit(suit: Suit, deck: Deck): DrawableResource {
        return when(deck){
            Deck.FIRST,
            Deck.FIFTH -> when (suit) {
                Suit.HEARTS -> Res.drawable.heart
                Suit.DIAMONDS -> Res.drawable.diamond
                Suit.CLUBS -> Res.drawable.club
                Suit.SPADES -> Res.drawable.spade
            }
            Deck.SECOND,
            Deck.THIRD,
            Deck.SEVENTH,
            Deck.EIGHTH -> when (suit) {
                Suit.HEARTS -> Res.drawable.heart_2
                Suit.DIAMONDS -> Res.drawable.dimond_2
                Suit.CLUBS -> Res.drawable.club_2
                Suit.SPADES -> Res.drawable.spade_2
            }
            Deck.FOURTH,
            Deck.SIXTH -> when (suit) {
                Suit.HEARTS -> Res.drawable.heart_3
                Suit.DIAMONDS -> Res.drawable.dimond_3
                Suit.CLUBS -> Res.drawable.club_3
                Suit.SPADES -> Res.drawable.spade_3
            }
        }

    }

    private fun handleDeckJack(suit: Suit, deck: Deck): DrawableResource {
        return when(deck){
            Deck.FIRST -> when(suit){
                Suit.HEARTS -> Res.drawable.JH
                Suit.DIAMONDS -> Res.drawable.JD
                Suit.CLUBS -> Res.drawable.JC
                Suit.SPADES -> Res.drawable.JS
            }
            Deck.SECOND -> when(suit){
                Suit.HEARTS -> Res.drawable.JH_2
                Suit.DIAMONDS -> Res.drawable.JD_2
                Suit.CLUBS -> Res.drawable.JC_2
                Suit.SPADES -> Res.drawable.JS_2
            }
            Deck.THIRD -> when(suit){
                Suit.HEARTS -> Res.drawable.JH_3
                Suit.DIAMONDS -> Res.drawable.JD_3
                Suit.CLUBS -> Res.drawable.JC_3
                Suit.SPADES -> Res.drawable.JS_3
            }
            Deck.FOURTH -> when(suit){
                Suit.HEARTS -> Res.drawable.JH_4
                Suit.DIAMONDS -> Res.drawable.JD_4
                Suit.CLUBS -> Res.drawable.JC_4
                Suit.SPADES -> Res.drawable.JS_4
            }
            Deck.FIFTH -> when(suit){
                Suit.HEARTS -> Res.drawable.JH_5
                Suit.DIAMONDS -> Res.drawable.JD_5
                Suit.CLUBS -> Res.drawable.JC_5
                Suit.SPADES -> Res.drawable.JS_5
            }
            Deck.SIXTH -> when(suit){
                Suit.HEARTS -> Res.drawable.JH_6
                Suit.DIAMONDS -> Res.drawable.JD_6
                Suit.CLUBS -> Res.drawable.JC_6
                Suit.SPADES -> Res.drawable.JS_6
            }
            Deck.SEVENTH -> when(suit){
                Suit.HEARTS -> Res.drawable.JH_7
                Suit.DIAMONDS -> Res.drawable.JD_7
                Suit.CLUBS -> Res.drawable.JC_7
                Suit.SPADES -> Res.drawable.JS_7
            }
            Deck.EIGHTH -> when(suit){
                Suit.HEARTS -> Res.drawable.JH_8
                Suit.DIAMONDS -> Res.drawable.JD_8
                Suit.CLUBS -> Res.drawable.JC_8
                Suit.SPADES -> Res.drawable.JS_8
            }
        }
    }

    private fun handleDeckQueen(suit: Suit, deck: Deck): DrawableResource {
        return when(deck){
            Deck.FIRST -> when(suit){
                Suit.HEARTS -> Res.drawable.QH
                Suit.DIAMONDS -> Res.drawable.QD
                Suit.CLUBS -> Res.drawable.QC
                Suit.SPADES -> Res.drawable.QS
            }
            Deck.SECOND -> when(suit){
                Suit.HEARTS -> Res.drawable.QH_2
                Suit.DIAMONDS -> Res.drawable.QD_2
                Suit.CLUBS -> Res.drawable.QC_2
                Suit.SPADES -> Res.drawable.QS_2
            }
            Deck.THIRD -> when(suit){
                Suit.HEARTS -> Res.drawable.QH_3
                Suit.DIAMONDS -> Res.drawable.QD_3
                Suit.CLUBS -> Res.drawable.QC_3
                Suit.SPADES -> Res.drawable.QS_3
            }
            Deck.FOURTH -> when(suit){
                Suit.HEARTS -> Res.drawable.QH_4
                Suit.DIAMONDS -> Res.drawable.QD_4
                Suit.CLUBS -> Res.drawable.QC_4
                Suit.SPADES -> Res.drawable.QS_4
            }
            Deck.FIFTH -> when(suit){
                Suit.HEARTS -> Res.drawable.QH_5
                Suit.DIAMONDS -> Res.drawable.QD_5
                Suit.CLUBS -> Res.drawable.QC_5
                Suit.SPADES -> Res.drawable.QS_5
            }
            Deck.SIXTH -> when(suit){
                Suit.HEARTS -> Res.drawable.QH_6
                Suit.DIAMONDS -> Res.drawable.QD_6
                Suit.CLUBS -> Res.drawable.QC_6
                Suit.SPADES -> Res.drawable.QS_6
            }
            Deck.SEVENTH -> when(suit){
                Suit.HEARTS -> Res.drawable.QH_7
                Suit.DIAMONDS -> Res.drawable.QD_7
                Suit.CLUBS -> Res.drawable.QC_7
                Suit.SPADES -> Res.drawable.QS_7
            }
            Deck.EIGHTH -> when(suit){
                Suit.HEARTS -> Res.drawable.QH_8
                Suit.DIAMONDS -> Res.drawable.QD_8
                Suit.CLUBS -> Res.drawable.QC_8
                Suit.SPADES -> Res.drawable.QS_8
            }
        }
    }

    private fun handleDeckKing(suit: Suit, deck: Deck): DrawableResource {
        return when(deck){
            Deck.FIRST -> when(suit){
                Suit.HEARTS -> Res.drawable.KH
                Suit.DIAMONDS -> Res.drawable.KD
                Suit.CLUBS -> Res.drawable.KC
                Suit.SPADES -> Res.drawable.KS
            }
            Deck.SECOND -> when(suit){
                Suit.HEARTS -> Res.drawable.KH_2
                Suit.DIAMONDS -> Res.drawable.KD_2
                Suit.CLUBS -> Res.drawable.KC_2
                Suit.SPADES -> Res.drawable.KS_2
            }
            Deck.THIRD -> when(suit){
                Suit.HEARTS -> Res.drawable.KH_3
                Suit.DIAMONDS -> Res.drawable.KD_3
                Suit.CLUBS -> Res.drawable.KC_3
                Suit.SPADES -> Res.drawable.KS_3
            }
            Deck.FOURTH -> when(suit){
                Suit.HEARTS -> Res.drawable.KH_4
                Suit.DIAMONDS -> Res.drawable.KD_4
                Suit.CLUBS -> Res.drawable.KC_4
                Suit.SPADES -> Res.drawable.KS_4
            }
            Deck.FIFTH -> when(suit){
                Suit.HEARTS -> Res.drawable.KH_5
                Suit.DIAMONDS -> Res.drawable.KD_5
                Suit.CLUBS -> Res.drawable.KC_5
                Suit.SPADES -> Res.drawable.KS_5
            }
            Deck.SIXTH -> when(suit){
                Suit.HEARTS -> Res.drawable.KH_6
                Suit.DIAMONDS -> Res.drawable.KD_6
                Suit.CLUBS -> Res.drawable.KC_6
                Suit.SPADES -> Res.drawable.KS_6
            }
            Deck.SEVENTH -> when(suit){
                Suit.HEARTS -> Res.drawable.KH_7
                Suit.DIAMONDS -> Res.drawable.KD_7
                Suit.CLUBS -> Res.drawable.KC_7
                Suit.SPADES -> Res.drawable.KS_7
            }
            Deck.EIGHTH -> when(suit){
                Suit.HEARTS -> Res.drawable.KH_8
                Suit.DIAMONDS -> Res.drawable.KD_8
                Suit.CLUBS -> Res.drawable.KC_8
                Suit.SPADES -> Res.drawable.KS_8
            }
        }
    }

    private fun handleLowRank(rank: String, suit: Suit, deck: Deck): CardResource {
        val suitRes = handleSuit(suit, deck)
        return CardResource(
            rank = rank,
            suit = suitRes,
            image = suitRes
        )
    }
}