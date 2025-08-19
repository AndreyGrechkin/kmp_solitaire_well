package models

sealed class Screen {
    data object WellMainScreen : Screen()
    data class Settings(val data: UserData) : Screen()
}