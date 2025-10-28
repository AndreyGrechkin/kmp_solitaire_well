package com.defey.solitairewell.managers.ads

import android.content.Context
import com.vk.api.sdk.VK

/**
 * ✅ Реализация AdManager для VK Рекламы на Android
 */
class VkAdManager(
    private val context: Context
) : AdManager {

    override val isInterstitialReady: Boolean
        get() = true

    override val isRewardedReady: Boolean
        get() = true

    private var isInitialized = false

    override fun initialize() {
        if (isInitialized) return

        // ✅ Инициализация VK SDK
        VK.initialize(context)
        isInitialized = true

        println("VK Ads: Initialized successfully")
    }

    override fun showBanner(placement: String) {
        // ✅ Реализацию баннеров добавим завтра
        println("VK Ads: Showing banner at $placement")
    }

    override fun hideBanner() {
        println("VK Ads: Hiding banner")
    }

    override suspend fun showInterstitial(placement: String): Boolean {
        println("VK Ads: Showing interstitial at $placement")
        return true
    }

    override suspend fun showRewardedVideo(
        placement: String,
        onReward: (Int) -> Unit
    ): Boolean {
        println("VK Ads: Showing rewarded video at $placement")
        onReward(1) // Тестовая награда
        return true
    }
}