package factories

import com.defey.solitairewell.resources.*
import com.defey.solitairewell.resources.Res
import models.Card
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