package managers

import androidx.compose.ui.text.intl.Locale

// Мультиплатформенное представление локали
// Интерфейс для мультиплатформенной локали
interface AppLocale {
    val languageCode: String
    val countryCode: String?
    val displayName: String
}

// Expect функции для создания локалей
expect fun createSystemAppLocale(): AppLocale
expect fun createAppLocale(languageCode: String, countryCode: String? = null): AppLocale
