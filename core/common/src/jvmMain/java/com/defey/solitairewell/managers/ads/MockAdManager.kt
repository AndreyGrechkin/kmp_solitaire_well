package com.defey.solitairewell.managers.ads

class MockAdManager : AdManager {
    override fun initialize() {
        println("Mock Ads: Initialized (ads disabled)")
    }

    override fun showBanner(placement: String) {
        println("Mock Ads: Banner at $placement")
    }

    override fun hideBanner() {
        println("Mock Ads: Hide banner")
    }

    override suspend fun showInterstitial(placement: String): Boolean {
        println("Mock Ads: Interstitial at $placement")
        return true
    }

    override suspend fun showRewardedVideo(
        placement: String,
        onReward: (Int) -> Unit
    ): Boolean {
        println("Mock Ads: Rewarded video at $placement")
        onReward(1)
        return true
    }

    override val isInterstitialReady: Boolean
        get() = true

    override val isRewardedReady: Boolean
        get() = true
}