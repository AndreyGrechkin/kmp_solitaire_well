package com.defey.solitairewell.managers.billing

/**
 * ✅ Управление In-App Purchases
 * Отвечает за:
 * - Инициализацию биллинг-системы
 * - Покупку удаления рекламы
 * - Верификацию покупок
 * - Восстановление покупок
 */
interface PurchaseManager {
//    fun initialize()
//
//    // ✅ Проверка, включена ли реклама (покупка не совершена)
//    fun areAdsEnabled(): Boolean
//
//    // ✅ Покупка удаления рекламы
//    suspend fun purchaseRemoveAds(): PurchaseResult
//
//    // ✅ Восстановление покупок
//    suspend fun restorePurchases(): RestoreResult
//
//    // ✅ Получение информации о продуктах
//    suspend fun getProducts(): List<ProductInfo>
}

/**
 * ✅ Информация о продукте
 */
data class ProductInfo(
    val id: String,
    val name: String,
    val description: String,
    val price: String
)

/**
 * ✅ Результат покупки
 */
sealed class PurchaseResult {
    object Success : PurchaseResult()
    data class Error(val message: String) : PurchaseResult()
    object Cancelled : PurchaseResult()
}

/**
 * ✅ Результат восстановления покупок
 */
sealed class RestoreResult {
    object Success : PurchaseResult()
    data class Error(val message: String) : PurchaseResult()
}