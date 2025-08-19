package logic

import model.CardStack

interface GameSetupFactory {
    fun createNewGame(): List<CardStack>
}