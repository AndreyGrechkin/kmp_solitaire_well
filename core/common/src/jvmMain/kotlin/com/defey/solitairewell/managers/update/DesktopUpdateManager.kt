package com.defey.solitairewell.managers.update

/**
 * ✅ Проверяльщик обновлений для Desktop (заглушка)
 */
class DesktopUpdateManager : UpdateManager {
    override fun checkForUpdates(
        onUpdateReady: () -> Unit,
        onNoUpdate: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
    }

    override fun completeUpdate(
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
    }
}