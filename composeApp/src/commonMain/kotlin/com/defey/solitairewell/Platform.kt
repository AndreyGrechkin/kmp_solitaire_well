package com.defey.solitairewell

import androidx.compose.runtime.Composable

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Composable
expect fun BackHandler(enabled: Boolean, onBack: () -> Unit)