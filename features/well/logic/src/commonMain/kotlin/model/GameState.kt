package model

import models.Card

data class GameState(
    val stock: List<Card>,         // центральная стопка (рубашкой вниз)
    val wells: List<List<Card>>,   // 4 колодца (по 1 карте в начале)
    val foundations: List<List<Card>> // 4 фундамента (A→K)
)
