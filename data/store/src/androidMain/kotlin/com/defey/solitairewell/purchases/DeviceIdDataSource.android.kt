package com.defey.solitairewell.purchases

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DeviceIdDataSource(
    private val context: Context,
) {

    @SuppressLint("HardwareIds")
    fun get(): String =
        runCatching {
            Settings
                .Secure
                .getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }.getOrElse { "unknown android id" }
}