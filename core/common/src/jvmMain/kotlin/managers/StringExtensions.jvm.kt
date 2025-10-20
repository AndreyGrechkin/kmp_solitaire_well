package managers


import androidx.compose.runtime.Composable
import java.util.Locale

@Composable
actual fun LanguageManager.getString(resource: StringResource): String {
    val context = LocalContext.current

    val appLocale = this.getCurrentAppLocale()
    val locale = Locale(appLocale.languageCode, appLocale.countryCode ?: "")

    // Создаем конфигурацию с нужной локалью
    val resources = context.resources
    val configuration = resources.configuration
    val originalLocale = configuration.locale

    return try {
        // Временно меняем локаль
        configuration.setLocale(locale)
        val localizedContext = context.createConfigurationContext(configuration)

        if (resource.formatArgs.isEmpty()) {
            localizedContext.getString(resource.id)
        } else {
            localizedContext.getString(resource.id, *resource.formatArgs.toTypedArray())
        }
    } finally {
        // Восстанавливаем оригинальную локаль
        configuration.setLocale(originalLocale)
    }
}