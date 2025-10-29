package com.defey.solitairewell.managers.update

/**
 * ✅ Проверяльщик обновлений для iOS (заглушка)
 */
class IosUpdateManager : UpdateManager {
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