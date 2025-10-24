package managers

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.StateFlow

@Stable
interface LanguageManager {
    val currentLanguage: AppLanguage
    val languageFlow: StateFlow<AppLanguage>
    fun setLanguage(language: AppLanguage)
    fun getAvailableLanguages(): List<AppLanguage>
}