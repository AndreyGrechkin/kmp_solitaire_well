package com.defey.solitairewell

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import platform.Foundation.NSUserDefaults


internal class IosKeyValueStorage : KeyValueStorage {

    private val userDefaults = NSUserDefaults.standardUserDefaults
    private val _updates = MutableSharedFlow<Pair<String, String?>>(replay = 1)

    override suspend fun putString(key: String, value: String) {
        userDefaults.setObject(value, forKey = key)
        userDefaults.synchronize()
        _updates.emit(key to value)
    }

    override suspend fun getString(key: String): String? {
        return userDefaults.stringForKey(key)
    }

    override suspend fun remove(key: String) {
        userDefaults.removeObjectForKey(key)
        userDefaults.synchronize()
        _updates.emit(key to null)
    }

    override fun setString(key: String, value: String) {
        userDefaults.setObject(value, forKey = key)
        userDefaults.synchronize()
    }

    override fun getStringTab(key: String): String? {
        return userDefaults.stringForKey(key)
    }

    override fun getStringFlow(key: String): Flow<String?> {
        return _updates
            .filter { it.first == key }
            .map { it.second }
            .onStart { emit(getString(key)) }
    }
}