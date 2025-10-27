package com.defey.solitairewell.utils

import androidx.compose.runtime.Composable
import com.defey.solitairewell.OrientationController


@Composable
actual fun createOrientationController(): OrientationController {
    return DesktopOrientationController()
}

class DesktopOrientationController : OrientationController {

    @Composable
    override fun LockPortraitOrientation() {}

    @Composable
    override fun LockLandscapeOrientation() {}
}