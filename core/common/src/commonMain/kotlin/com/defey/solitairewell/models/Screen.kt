package models

sealed class Screen(val route: String) {
    data object WellMainScreen : Screen("well")
    data class Settings(val data: UserData) : Screen("settings/{userName}?score={score}") {
        companion object {
            const val ROUTE = "settings/{userName}?score={score}"
            fun createRoute(userName: String, score: Int = 0) = "settings/$userName?score=$score"
        }
    }
}