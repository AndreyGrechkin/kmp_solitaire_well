package com.defey.solitairewell

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformDatabaseModule(
    fileName: String,
): Module = module(createdAtStart = true) {
    single<AppDatabase> { getDatabase(get(), fileName) }
}

private fun getDatabase(
    context: Context,
    fileName: String,
): AppDatabase = Room
    .databaseBuilder<AppDatabase>(
        context = context.applicationContext,
        name = context.applicationContext.getDatabasePath(fileName).absolutePath
    )
    .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .build()