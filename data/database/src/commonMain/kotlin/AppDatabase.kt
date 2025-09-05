
import androidx.room.Database
import androidx.room.RoomDatabase
import dao.WellCardDao
import dao.WellGameStateDao
import tables.WellCardEntity
import tables.WellGameStateEntity

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