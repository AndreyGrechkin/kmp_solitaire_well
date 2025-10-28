package com.defey.solitairewell.managers

import android.util.Log
import com.defey.solitairewell.core.common.BuildConfig

class BuildConfigCheck {
    fun logBuildConfig() {
        Log.d("BuildConfig", "SHOW_ADS: ${ BuildConfig.SHOW_ADS}")
        Log.d("BuildConfig", "AD_NETWORK: ${BuildConfig.AD_NETWORK}")
        Log.d("BuildConfig", "DEBUG: ${BuildConfig.DEBUG}")
        Log.d("BuildConfig", "BUILD_TYPE: ${BuildConfig.BUILD_TYPE}")
    }
}