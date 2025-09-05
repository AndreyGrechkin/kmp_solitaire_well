package tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "well_game_state")
data class WellGameStateEntity(
    @PrimaryKey
    val id: Int = 0,
    val countGameStack: Int?,
    val step: Int?
)
