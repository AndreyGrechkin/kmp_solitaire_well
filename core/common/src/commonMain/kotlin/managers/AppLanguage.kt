package managers

import androidx.compose.runtime.Stable

@Stable
enum class AppLanguage(val appLocale: AppLocale, val displayName: String) {
    SYSTEM(createSystemAppLocale(), "System"),
    ENGLISH(createAppLocale("en"), "English"),
    RUSSIAN(createAppLocale("ru"), "Русский");

    companion object {
        fun fromLanguageCode(code: String): AppLanguage {
            return when (code) {
                "en" -> ENGLISH
                "ru" -> RUSSIAN
                else -> SYSTEM
            }
        }
    }
}