package com.defey.solitairewell.managers.update

/**
 * ✅ Проверка обновлений приложения
 * Отвечает за:
 * - Проверку наличия новой версии в магазине
 * - Показ уведомлений об обновлениях
 * - Перенаправление в магазин для обновления
 */
interface UpdateManager {
    fun checkForUpdates(
        onUpdateReady: () -> Unit,
        onNoUpdate: () -> Unit,
        onError: (Throwable) -> Unit
    )

    fun completeUpdate(
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    )
}