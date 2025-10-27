package com.defey.solitairewell.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import com.defey.solitairewell.OrientationController
import platform.UIKit.UIApplication
import platform.UIKit.UIInterfaceOrientationMask
import platform.UIKit.supportedInterfaceOrientations

@Composable
actual fun createOrientationController(): OrientationController {
    return IOSOrientationController()
}

class IOSOrientationController : OrientationController {

    @Composable
    override fun LockPortraitOrientation() {
        LockScreenOrientation(ScreenOrientation.PORTRAIT)
        UnlockScreenOrientationOnDispose()
    }

    @Composable
    override fun LockLandscapeOrientation() {
        LockScreenOrientation(ScreenOrientation.LANDSCAPE)
        UnlockScreenOrientationOnDispose()
    }

    @Composable
    private fun LockScreenOrientation(orientation: ScreenOrientation) {
        val mask = when (orientation) {
            ScreenOrientation.PORTRAIT -> UIInterfaceOrientationMask.UIInterfaceOrientationMaskPortrait
            ScreenOrientation.LANDSCAPE -> UIInterfaceOrientationMask.UIInterfaceOrientationMaskLandscape
        }

        LaunchedEffect(orientation) {
            setSupportedInterfaceOrientations(mask)
        }
    }

    @Composable
    private fun UnlockScreenOrientationOnDispose() {
        DisposableEffect(Unit) {
            onDispose {
                setSupportedInterfaceOrientations(UIInterfaceOrientationMask.UIInterfaceOrientationMaskAll)
            }
        }
    }

    private fun setSupportedInterfaceOrientations(mask: UInt) {
        val application = UIApplication.sharedApplication
        val delegate = application.delegate
        val window = delegate?.window

        // Устанавливаем ориентацию для rootViewController
        window?.rootViewController?.supportedInterfaceOrientations = mask

        // Принудительно обновляем ориентацию устройства
        val device = platform.UIKit.UIDevice.currentDevice
        val currentOrientation = device.orientation
        device.setValueForKey(currentOrientation, "orientation")
    }
}