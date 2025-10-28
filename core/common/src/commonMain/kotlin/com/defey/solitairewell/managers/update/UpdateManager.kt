package com.defey.solitairewell.managers.update

/**
 * ✅ Проверка обновлений приложения
 * Отвечает за:
 * - Проверку наличия новой версии в магазине
 * - Показ уведомлений об обновлениях
 * - Перенаправление в магазин для обновления
 */
interface UpdateManager {
    suspend fun checkForUpdate(): Boolean
    // ✅ Детальную логику добавим на этапе реализации
}