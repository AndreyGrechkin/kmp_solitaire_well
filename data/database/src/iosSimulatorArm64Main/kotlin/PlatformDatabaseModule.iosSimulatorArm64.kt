import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

internal actual fun platformDatabaseModule(
    fileName: String,
): Module = module {
    single<AppDatabase>(createdAtStart = true) { getDatabase(fileName) }
}

private fun getDatabase(
    fileName: String,
): AppDatabase = Room
    .databaseBuilder<AppDatabase>(
        name = NSHomeDirectory() + "/$fileName",
        factory = { AppDatabase::class.instantiateImpl() }
    )
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .build()