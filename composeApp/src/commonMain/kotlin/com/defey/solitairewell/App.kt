package com.defey.solitairewell


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import base.NavigationCommand
import base.NavigationManager
import com.defey.solitairewell.screens.WellScreen
import models.Screen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import com.defey.solitairewell.screen.SettingsScreen
import managers.LanguageManager
import com.defey.solitairewell.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme(darkTheme = false) {
        val navController = rememberNavController()
        val navigationManager: NavigationManager = koinInject<NavigationManager>()
        val languageManager: LanguageManager = koinInject<LanguageManager>()
        val navigationState by navigationManager.navigationState.collectAsState()
        val languageState by languageManager.languageFlow.collectAsState()

        LaunchedEffect(navigationState, languageState) {
            when (val command = navigationState) {
                is NavigationCommand.Navigate -> {
                    navController.navigate(command.route) {
                        command.options.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) {
                                inclusive = command.options.inclusive
                                saveState = command.options.saveState
                            }
                        }
                        restoreState = command.options.restoreState
                    }
                    navigationManager.clearNavigation()
                }

                is NavigationCommand.PopBackStack -> {
                    navController.popBackStack()
                    navigationManager.clearNavigation()
                }

                is NavigationCommand.PopBackStackTo -> {
                    navController.popBackStack(command.route, command.inclusive)
                    navigationManager.clearNavigation()
                }

                NavigationCommand.Idle -> {}
            }
        }

        NavHost(
            navController = navController,
            startDestination = Screen.WellMainScreen.route
        ) {
            composable(Screen.WellMainScreen.route) {
                WellScreen()
            }

            composable(
                route = Screen.Settings.ROUTE,
                arguments = listOf(
                    navArgument("userName") {
                        type = NavType.StringType
                    },
                    navArgument("score") {
                        type = NavType.IntType
                        defaultValue = 0
                    }
                )
            ) {
                SettingsScreen()
            }
        }
    }
}