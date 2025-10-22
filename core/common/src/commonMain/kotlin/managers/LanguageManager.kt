package managers

import androidx.compose.runtime.Stable

@Stable
interface LanguageManager {
    val currentLanguage: AppLanguage
    fun setLanguage(language: AppLanguage)
    fun getAvailableLanguages(): List<AppLanguage>
}