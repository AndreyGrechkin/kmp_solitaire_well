package managers

import androidx.compose.runtime.Composable


data class StringResource(
    val id: Int,
    val formatArgs: List<Any> = emptyList()
)

@Composable
expect fun getString(resource: StringResource, languageManager: LanguageManager): String