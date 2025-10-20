package managers

import androidx.compose.runtime.Composable



@Composable
actual fun LanguageManager.getString(resource: StringResource): String {
    // На iOS используем стандартный механизм Compose Multiplatform
    return if (resource.formatArgs.isEmpty()) {
        composeStringResource(resource.id)
    } else {
        composeStringResource(resource.id, *resource.formatArgs.toTypedArray())
    }
}