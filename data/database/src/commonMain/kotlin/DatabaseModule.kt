import dao.WellCardDao
import dao.WellGameStateDao
import org.koin.core.module.Module
import org.koin.dsl.module


val databaseModule
    get() = platformDatabaseModule(fileName = "database.db")
        .apply {
            single<WellCardDao> { get<AppDatabase>().wellCardDao() }
            single<WellGameStateDao> { get<AppDatabase>().wellGameStateDao() }
        }

val storageModule
    get() = module {
        includes(platformStorageModule())
    }

internal expect fun platformStorageModule(): Module
