package com.defey.solitairewell.managers.ads

/**
 * ✅ Desktop реализация конфигурации рекламы
 * На десктопе реклама отключена
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AdConfig {
    actual val showAds: Boolean
        get() = false
    actual val adNetwork: String
        get() = "VK_ADS"

    // ✅ ТЕСТОВЫЕ ID VK РЕКЛАМЫ
    actual val bannerAdId: String = "banner-320x50"
    actual val interstitialAdId: String = "interstitial"
    actual val rewardedAdId: String = "rewarded"

    actual val interstitialFrequency: Int = 3
    actual val initialCredits: Int = 5
}