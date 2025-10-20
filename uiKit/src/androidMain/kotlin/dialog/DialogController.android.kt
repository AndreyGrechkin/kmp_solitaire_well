package dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * Android реализация rememberDialogController.
 * Создает экземпляр контроллера и запоминает его.
 */
@Composable
actual fun rememberDialogController(): DialogController {
    return DialogController()
}

/**
 * Android реализация PlatformDialog.
 * Использует стандартный Dialog из Compose.
 */
@Composable
actual fun PlatformDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
        content = content
    )
}