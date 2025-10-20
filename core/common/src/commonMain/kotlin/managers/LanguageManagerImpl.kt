package managers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class LanguageManagerImpl : LanguageManager {
    override var currentLanguage by mutableStateOf(AppLanguage.SYSTEM)
        private set

    override fun setLanguage(language: AppLanguage) {
        currentLanguage = language
    }

    override fun getCurrentAppLocale(): AppLocale {
        return when (currentLanguage) {
            AppLanguage.SYSTEM -> createSystemAppLocale()
            AppLanguage.ENGLISH -> createAppLocale("en")
            AppLanguage.RUSSIAN -> createAppLocale("ru")
        }
    }

    override fun getAvailableLanguages(): List<AppLanguage> {
        return AppLanguage.entries.toList()
    }
}