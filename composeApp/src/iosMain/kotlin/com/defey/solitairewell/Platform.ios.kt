package com.defey.solitairewell

import androidx.compose.runtime.Composable
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    // Для iOS можно использовать SwiftUI-аналоги или оставить пустым
}