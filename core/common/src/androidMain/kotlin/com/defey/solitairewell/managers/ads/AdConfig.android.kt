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
}