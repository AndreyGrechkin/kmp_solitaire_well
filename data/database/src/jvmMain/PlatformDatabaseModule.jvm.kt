import org.koin.dsl.module


internal actual fun platformDatabaseModule(
    fileName: String,
): Module = module(createdAtStart = true) {
    single<AppDatabase> { createJvmDatabase(fileName) }
}

private fun createJvmDatabase(fileName: String): AppDatabase {
    // Для JVM используем in-memory базу или файловую SQLite
    return JvmAppDatabase(fileName)
}