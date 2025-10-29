package managers

import com.defey.solitairewell.managers.Localization
import java.util.Locale
import java.util.ResourceBundle

internal class JvmLocalization : Localization {
    override fun applyLanguage(iso: String) {
        val locale = Locale(iso)
        Locale.setDefault(locale)
        try {
            val bundle = ResourceBundle.getBundle("messages", locale)
        } catch (e: Exception) {
        }
    }

    override fun getSystemLanguageCode(): String {
        return System.getProperty("user.language") ?: "en"
    }
}