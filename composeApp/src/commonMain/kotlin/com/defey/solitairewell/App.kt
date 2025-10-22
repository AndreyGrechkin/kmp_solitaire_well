package com.defey.solitairewell


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import base.NavigationCommand
import base.NavigationManager
import com.defey.solitairewell.screens.WellScreen
import models.Screen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import screen.SettingsScreen
import theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme(darkTheme = false) {
        val navController = rememberNavController()
        val navigationManager: NavigationManager = koinInject<NavigationManager>()
        val navigationState by navigationManager.navigationState.collectAsState()
        LaunchedEffect(navigationState) {
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
            composable(Screen.Settings.ROUTE) { backStackEntry ->
                SettingsScreen()
            }

//        composable(Screen.Game.ROUTE) { backStackEntry ->
//            val gameId = backStackEntry.arguments?.getString("gameId") ?: ""
//            GameScreen(gameId = gameId)
//        }
        }
    }
}