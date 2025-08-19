package com.defey.solitairewell


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme(darkTheme = false) {
        NavHost()
    }
}