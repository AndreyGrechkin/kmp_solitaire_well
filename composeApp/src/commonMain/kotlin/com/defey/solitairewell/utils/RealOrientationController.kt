package com.defey.solitairewell.utils

import androidx.compose.runtime.Composable
import com.defey.solitairewell.OrientationController

@Composable
expect fun createOrientationController(): OrientationController

internal enum class ScreenOrientation {
    PORTRAIT, LANDSCAPE
}