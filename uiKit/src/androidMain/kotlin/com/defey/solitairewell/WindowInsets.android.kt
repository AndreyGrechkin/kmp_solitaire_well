package com.defey.solitairewell

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity

@Composable
actual fun Modifier.statusBarPadding(): Modifier {
    val density = LocalDensity.current
   return this
        .padding(top = WindowInsets.statusBars.getTop(density).pxToDp(getDensity()))
}