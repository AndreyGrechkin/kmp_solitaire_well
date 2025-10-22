package managers

import repository.StorageRepository

class LanguageManagerImpl(
    private val localization: Localization,
    private val repository: StorageRepository,
) : LanguageManager {
    override val currentLanguage : AppLanguage
        get() = AppLanguage.fromLanguageCode(repository.getLanguage())

    override fun setLanguage(language: AppLanguage) {
        repository.setLanguage(language.iso)

        if (language != AppLanguage.SYSTEM) {
            localization.applyLanguage(language.iso)
        } else {
            localization.applyLanguage(localization.getSystemLanguageCode())
        }
    }

    init {
        when (currentLanguage) {
            AppLanguage.SYSTEM -> localization.applyLanguage(localization.getSystemLanguageCode())
            else -> localization.applyLanguage(currentLanguage.iso)
        }
    }

    override fun getAvailableLanguages(): List<AppLanguage> {
        return AppLanguage.entries
    }
}