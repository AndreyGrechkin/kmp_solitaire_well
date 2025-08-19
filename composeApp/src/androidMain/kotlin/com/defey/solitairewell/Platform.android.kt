package com.defey.solitairewell

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    BackHandler(enabled, onBack)
}