import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.io.File
import java.util.Properties


internal class JvmKeyValueStorage(
    private val fileName: String = "app_prefs.properties"
) : KeyValueStorage {

    private val propertiesFile = File(fileName)
    private val properties = Properties()
    private val _updates = MutableSharedFlow<Pair<String, String?>>(replay = 1)

    init {
        if (propertiesFile.exists()) {
            propertiesFile.inputStream().use { properties.load(it) }
        }
    }

    private fun saveProperties() {
        propertiesFile.outputStream().use { properties.store(it, null) }
    }

    override suspend fun putString(key: String, value: String) {
        properties.setProperty(key, value)
        saveProperties()
        _updates.emit(key to value)
    }

    override suspend fun getString(key: String): String? {
        return properties.getProperty(key)
    }

    override suspend fun remove(key: String) {
        properties.remove(key)
        saveProperties()
        _updates.emit(key to null)
    }

    override fun getStringFlow(key: String): Flow<String?> {
        return _updates
            .filter { it.first == key }
            .map { it.second }
            .onStart {
                emit(getString(key))
            }
    }
}