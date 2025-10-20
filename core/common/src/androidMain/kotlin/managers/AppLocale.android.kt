package managers

import java.util.Locale

actual fun createSystemAppLocale(): AppLocale {
    val systemLocale = Locale.getDefault()
    return AndroidAppLocale(systemLocale.language, systemLocale.country)
}

actual fun createAppLocale(languageCode: String, countryCode: String?): AppLocale {
    return AndroidAppLocale(languageCode, countryCode)
}

class AndroidAppLocale(
    override val languageCode: String,
    override val countryCode: String?
) : AppLocale {
    private val javaLocale: Locale by lazy {
        if (countryCode != null) {
            Locale(languageCode, countryCode)
        } else {
            Locale(languageCode)
        }
    }

    override val displayName: String
        get() = javaLocale.displayName
}