import kotlinx.coroutines.flow.Flow


interface KeyValueStorage {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun remove(key: String)

    fun getStringFlow(key: String): Flow<String?>
}