package com.defey.solitairewell

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(
            appModules
        )
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "SolitaireWell",
    ) {
        App()
    }
}