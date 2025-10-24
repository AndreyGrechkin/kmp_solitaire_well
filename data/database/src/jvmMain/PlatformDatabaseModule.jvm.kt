import org.koin.dsl.module

internal actual fun platformDatabaseModule(
    fileName: String,
): Module = module(createdAtStart = true) {
    single<AppDatabase> { createJvmDatabase(fileName) }
}

private fun createJvmDatabase(fileName: String): AppDatabase {
    return JvmAppDatabase(fileName)
}