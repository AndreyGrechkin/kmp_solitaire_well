package com.defey.solitairewell.managers.ads

/**
 * ✅ Desktop реализация конфигурации рекламы
 * На десктопе реклама отключена
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AdConfig {
    actual val showAds: Boolean
        get() = false
}