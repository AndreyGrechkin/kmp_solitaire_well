package com.defey.solitairewell

import androidx.compose.runtime.Composable

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JComponent
import javax.swing.KeyStroke

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    val currentOnBack by rememberUpdatedState(onBack)

    DisposableEffect(enabled) {
        val listener = object : KeyListener {
            override fun keyTyped(e: KeyEvent?) {}
            override fun keyPressed(e: KeyEvent) {
                if (enabled && e.keyCode == KeyEvent.VK_ESCAPE) {
                    currentOnBack()
                }
            }

            override fun keyReleased(e: KeyEvent?) {}
        }

        val window = java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager().activeWindow
        window?.addKeyListener(listener)

        onDispose {
            window?.removeKeyListener(listener)
        }
    }
}