package com.defey.solitairewell.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.defey.solitairewell.tables.WellCardEntity

@Dao
interface WellCardDao {

    @Upsert()
    suspend fun setWellCards(cards: List<WellCardEntity>)

    @Query("SELECT * FROM well_cards")
    suspend fun getWellCards(): List<WellCardEntity>

    @Query("DELETE FROM well_cards")
    suspend fun deleteWellCards()

}