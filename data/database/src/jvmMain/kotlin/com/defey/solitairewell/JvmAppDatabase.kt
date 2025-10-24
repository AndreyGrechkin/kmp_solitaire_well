package com.defey.solitairewell

import androidx.room.InvalidationTracker
import com.defey.solitairewell.dao.WellCardDao
import com.defey.solitairewell.dao.WellGameStateDao

class JvmAppDatabase(private val fileName: String) : AppDatabase() {

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun wellCardDao(): WellCardDao {
        TODO("Not yet implemented")
    }

    override fun wellGameStateDao(): WellGameStateDao {
        TODO("Not yet implemented")
    }
}