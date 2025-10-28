package com.defey.solitairewell.managers.ads

/**
 * ✅ iOS реализация конфигурации рекламы
 * Пока заглушка - реклама отключена
 * На этапе реализации добавим поддержку AppLovin/AdMob
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AdConfig {
    actual val showAds: Boolean
        get() = false // ✅ Пока на iOS рекламы нет
}