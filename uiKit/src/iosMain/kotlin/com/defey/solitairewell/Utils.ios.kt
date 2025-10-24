package com.defey.solitairewell

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun rememberCardSize(): Dp {
    val width = getScreenMetrics().widthDp
    val size = ((width - (16.dp * 7)) / 5)
    return size
}