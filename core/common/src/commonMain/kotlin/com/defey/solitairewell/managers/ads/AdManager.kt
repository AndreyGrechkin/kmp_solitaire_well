package com.defey.solitairewell.managers.ads

/**
 * ✅ Основной интерфейс для управления рекламой
 * Отвечает за:
 * - Инициализацию рекламных SDK
 * - Показ баннеров, интерстишиалов, rewarded видео
 * - Управление доступностью рекламных блоков
 */
interface AdManager {
    fun initialize()

    // Баннеры
    fun showBanner(placement: String)
    fun hideBanner()

    // Интерстишиалы
    suspend fun showInterstitial(placement: String): Boolean
    val isInterstitialReady: Boolean

    // Rewarded Video
    suspend fun showRewardedVideo(
        placement: String,
        onReward: (reward: Int) -> Unit
    ): Boolean
    val isRewardedReady: Boolean


}