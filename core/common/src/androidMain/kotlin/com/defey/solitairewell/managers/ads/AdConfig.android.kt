package com.defey.solitairewell.managers.ads

import com.defey.solitairewell.core.common.BuildConfig

/**
 * ✅ Android реализация конфигурации рекламы
 * Использует BuildConfig для доступа к флагам из build.gradle
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AdConfig {
    actual val showAds: Boolean
        get() = BuildConfig.SHOW_ADS // ✅ Берем из конфигурации сборки
    actual val adNetwork: String
        get() = "VK_ADS"

    // ✅ ТЕСТОВЫЕ ID VK РЕКЛАМЫ
    actual val bannerAdId: String = "banner-320x50"
    actual val interstitialAdId: String = "interstitial"
    actual val rewardedAdId: String = "rewarded"

    actual val interstitialFrequency: Int = 3
    actual val initialCredits: Int = 5
}