import androidx.room.InvalidationTracker
import dao.WellCardDao
import kotlinx.coroutines.flow.Flow
import tables.DeckEntity

class JvmAppDatabase(private val fileName: String) : AppDatabase() {
    // Реализуйте все методы интерфейса AppDatabase для JVM
    // Например, используя JDBC, H2, SQLite JDBC driver и т.д.

//    override fun cardDao(): CardDao {
//        return JvmCardDao()
//    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

//    override fun close() {
//        // Закрытие соединений с БД
//    }

    override fun wellCardDao(): WellCardDao {
        TODO("Not yet implemented")
    }
}

class JvmCardDao : WellCardDao {

    override suspend fun insert(entity: DeckEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun get(): DeckEntity? {
       return null
    }

    override fun observe(): Flow<DeckEntity?> {
        TODO("Not yet implemented")
    }

    // Остальные методы интерфейса CardDao
}