package com.defey.solitairewell

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import androidx.core.content.edit


internal class AndroidKeyValueStorage(
    private val context: Context,
) : KeyValueStorage {

    private val sharedPreferences by lazy {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    private val _updates = MutableSharedFlow<Pair<String, String?>>(replay = 1)

    override suspend fun putString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
        _updates.emit(key to value)
    }

    override suspend fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override suspend fun remove(key: String) {
        sharedPreferences.edit { remove(key) }
        _updates.emit(key to null)
    }

    override fun setString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    override fun getStringTab(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun setLong(key: String, value: Long) {
        sharedPreferences.edit { putLong(key, value) }
    }

    override fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0L)
    }

    override fun getStringFlow(key: String): Flow<String?> {
        return _updates
            .filter { it.first == key }
            .map { it.second }
            .onStart { emit(getString(key)) }
    }
}