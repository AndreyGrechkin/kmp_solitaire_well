package com.defey.solitairewell.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * iOS реализация rememberDialogController.
 */
@Composable
actual fun rememberDialogController(): DialogController {
    return DialogController()
}

/**
 * iOS реализация PlatformDialog.
 * На iOS используется та же реализация, что и на других платформах.
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