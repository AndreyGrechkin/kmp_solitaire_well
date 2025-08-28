package com.defey.solitairewell

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import base.AnimatedNavHost
import base.NavigationLogger
import base.Router
import org.koin.compose.koinInject
import models.Screen
import screens.WellScreen
import setting.SettingsScreen

@Composable
fun NavHost() {
    val router = koinInject<Router>()
    val logger = koinInject<NavigationLogger>()
    logger.enable()
    router.setInitialScreen(Screen.WellMainScreen)
    val backstack by router.currentBackstack.collectAsState()
    val currentScreen = backstack.last()
    BackHandler(enabled = backstack.size > 1) { router.navigateBack() }

    AnimatedNavHost(
        router = router,
        currentScreen = currentScreen
    ) { screen ->
        when (currentScreen) {
            is Screen.WellMainScreen -> WellScreen()
            is Screen.Settings -> SettingsScreen()
        }
    }
}