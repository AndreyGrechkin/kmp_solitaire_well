package com.defey.solitairewell.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.defey.solitairewell.dialog.DialogController

/**
 * Desktop реализация rememberDialogController.
 */
@Composable
actual fun rememberDialogController(): DialogController {
    return DialogController()
}

/**
 * Desktop реализация PlatformDialog.
 * Аналогична Android, но может иметь desktop-специфичные настройки.
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