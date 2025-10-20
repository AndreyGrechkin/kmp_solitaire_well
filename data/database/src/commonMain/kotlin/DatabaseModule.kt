import dao.WellCardDao
import dao.WellGameStateDao
import org.koin.core.module.Module
import org.koin.dsl.module
import repository.StorageRepository
import repository.StorageRepositoryImpl
import repository.WellRepository
import repository.WellRepositoryImpl


val databaseModule
    get() = platformDatabaseModule(fileName = "database.db")
        .apply {
            single<WellCardDao> { get<AppDatabase>().wellCardDao() }
            single<WellGameStateDao> { get<AppDatabase>().wellGameStateDao() }
        }

val dataRepositoryModule
    get() = module {
        single<StorageRepository>{ StorageRepositoryImpl(get()) }
        factory<WellRepository> { WellRepositoryImpl(get(), get()) }
    }

val storageModule
    get() = module {
        includes(platformStorageModule())
    }

internal expect fun platformStorageModule(): Module
