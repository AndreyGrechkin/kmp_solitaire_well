package managers

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@SuppressLint("LocalContextConfigurationRead")
@Composable
actual fun getString(resource: StringResource, languageManager: LanguageManager): String {
    val context = LocalContext.current

    return when (languageManager.currentLanguage) {
        AppLanguage.SYSTEM -> {
            // Используем системную локаль
            if (resource.formatArgs.isEmpty()) {
                context.getString(resource.id)
            } else {
                context.getString(resource.id, *resource.formatArgs.toTypedArray())
            }
        }
        else -> {
            // Используем выбранную локаль
            val appLocale = languageManager.getCurrentAppLocale()
            val locale = Locale(appLocale.languageCode, appLocale.countryCode ?: "")

            val resources = context.resources
            val configuration = Configuration(resources.configuration)
            val originalLocale = configuration.locale

            try {
                configuration.setLocale(locale)
                val localizedContext = context.createConfigurationContext(configuration)

                if (resource.formatArgs.isEmpty()) {
                    localizedContext.getString(resource.id)
                } else {
                    localizedContext.getString(resource.id, *resource.formatArgs.toTypedArray())
                }
            } finally {
                configuration.setLocale(originalLocale)
            }
        }
    }
}