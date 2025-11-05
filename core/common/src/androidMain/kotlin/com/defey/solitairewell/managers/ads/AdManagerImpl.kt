package com.defey.solitairewell.managers.ads

class AdManagerImpl : AdManager {

    private var gamesPlayed = 0

    override fun onGameFinished() {
        gamesPlayed++
        val shouldShowAd = gamesPlayed % INTERSTITIAL_FREQUENCY == 0
        if (shouldShowAd) {
            AdEventBus.showAd()
        }
    }

    companion object {
        private const val INTERSTITIAL_FREQUENCY = 3
    }
}