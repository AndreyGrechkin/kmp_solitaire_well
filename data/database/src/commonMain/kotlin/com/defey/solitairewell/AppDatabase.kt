package com.defey.solitairewell

import androidx.room.Database
import androidx.room.RoomDatabase
import com.defey.solitairewell.dao.WellCardDao
import com.defey.solitairewell.dao.WellGameStateDao
import com.defey.solitairewell.tables.WellCardEntity
import com.defey.solitairewell.tables.WellGameStateEntity

@Database(
    entities = [
        WellCardEntity::class,
        WellGameStateEntity::class,
    ],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wellCardDao(): WellCardDao
    abstract fun wellGameStateDao(): WellGameStateDao
}

private const val DATABASE_VERSION = 1