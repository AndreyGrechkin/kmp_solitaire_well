package com.defey.solitairewell.managers.ads

/**
 * ✅ Конфигурация рекламы для разных сборок и платформ
 * Содержит:
 * - Флаги включения/выключения рекламы
 * - ID рекламных блоков
 * - Настройки частоты показа
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AdConfig {
    val showAds: Boolean
    val adNetwork: String

    // ✅ Рекламные ID
    val bannerAdId: String
    val interstitialAdId: String
    val rewardedAdId: String

    // ✅ Настройки частоты
    val interstitialFrequency: Int
    val initialCredits: Int
}