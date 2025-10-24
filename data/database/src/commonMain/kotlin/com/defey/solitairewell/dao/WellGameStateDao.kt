package com.defey.solitairewell.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.defey.solitairewell.tables.WellGameStateEntity

@Dao
interface WellGameStateDao {

    @Upsert()
    suspend fun setWellGameState(state: WellGameStateEntity)

    @Query("SELECT * FROM well_game_state")
    suspend fun getWellGameState(): WellGameStateEntity?

    @Query("DELETE FROM well_game_state")
    suspend fun deleteWellGameState()
}