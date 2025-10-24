package com.defey.solitairewell.tables

import androidx.room.Entity

@Entity(
    tableName = "well_cards",
    primaryKeys = ["stackType", "stackIndex", "position"]
)
data class WellCardEntity(
    val stackType: String,
    val stackIndex: Int,
    val suit: String?,
    val rank: Int?,
    val isFaceUp: Boolean,
    val position: Int
)
