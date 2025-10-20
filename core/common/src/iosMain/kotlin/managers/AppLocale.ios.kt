package managers

import platform.Foundation.NSLocale
import platform.Foundation.NSLocaleIdentifier
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.Foundation.countryCode

actual fun createSystemAppLocale(): AppLocale {
    val currentLocale = NSLocale.currentLocale
    return IosAppLocale(
        languageCode = currentLocale.languageCode,
        countryCode = currentLocale.countryCode
    )
}

actual fun createAppLocale(languageCode: String, countryCode: String?): AppLocale {
    return IosAppLocale(languageCode, countryCode)
}

class IosAppLocale(
    override val languageCode: String,
    override val countryCode: String?
) : AppLocale {
    private val localeIdentifier: String by lazy {
        if (countryCode != null) {
            "${languageCode}_$countryCode"
        } else {
            languageCode
        }
    }

    private val nsLocale: NSLocale by lazy {
        NSLocale(localeIdentifier = localeIdentifier)
    }

    override val displayName: String
        get() = nsLocale.displayNameForKey(
            NSLocaleIdentifier,
            value = localeIdentifier
        ) ?: localeIdentifier
}