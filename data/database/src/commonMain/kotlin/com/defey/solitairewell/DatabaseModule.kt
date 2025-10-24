package com.defey.solitairewell

import com.defey.solitairewell.dao.WellCardDao
import com.defey.solitairewell.dao.WellGameStateDao
import org.koin.core.module.Module
import org.koin.dsl.module
import repository.StorageRepository
import com.defey.solitairewell.repository.StorageRepositoryImpl
import repository.WellRepository
import com.defey.solitairewell.repository.WellRepositoryImpl


val databaseModule
    get() = platformDatabaseModule(fileName = "database.db")
        .apply {
            single<WellCardDao> { get<AppDatabase>().wellCardDao() }
            single<WellGameStateDao> { get<AppDatabase>().wellGameStateDao() }
        }

val dataRepositoryModule
    get() = module {
        single<StorageRepository> { StorageRepositoryImpl(get()) }
        factory<WellRepository> { WellRepositoryImpl(get(), get()) }
    }

val storageModule
    get() = module {
        includes(platformStorageModule())
    }

internal expect fun platformStorageModule(): Module
