package com.defey.solitairewell

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.defey.solitairewell.managers.ads.AdEventBus
import com.defey.solitairewell.managers.analytics.AnalyticsManager
import com.defey.solitairewell.managers.update.UpdateManager
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader
import org.koin.android.ext.android.inject
import ru.rustore.sdk.pay.RuStorePayClient

class MainActivity : ComponentActivity() {

    private val updateManager: UpdateManager by inject()
    private val analytics: AnalyticsManager by inject()

    private val payClient: RuStorePayClient by inject()

    private var interstitialAd: InterstitialAd? = null
    private var interstitialAdLoader: InterstitialAdLoader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            payClient.getIntentInteractor().proceedIntent(intent)
        }
        analytics.logAppLaunch()
        AdEventBus.setOnShowAdListener { showAd() }

        setContent {
            App()
        }

        updateManager.checkForUpdates(
            onUpdateReady = {
                updateManager.completeUpdate(
                    onComplete = {
                        Toast.makeText(this, R.string.app_update_complete, Toast.LENGTH_LONG).show()
                    },
                    onError = {}
                )
            },
            onNoUpdate = {},
            onError = {}
        )

        interstitialAdLoader = InterstitialAdLoader(this).apply {
            setAdLoadListener(object : InterstitialAdLoadListener {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    this@MainActivity.interstitialAd = interstitialAd
                }

                override fun onAdFailedToLoad(error: AdRequestError) {
                }
            })
        }
        loadInterstitialAd()
    }

    private fun showAd() {
        interstitialAd?.apply {
            setAdEventListener(object : InterstitialAdEventListener {
                override fun onAdShown() {

                }

                override fun onAdFailedToShow(adError: AdError) {
                    destroyInterstitialAd()
                    loadInterstitialAd()
                }

                override fun onAdDismissed() {
                    destroyInterstitialAd()
                    loadInterstitialAd()
                }

                override fun onAdClicked() {
                }

                override fun onAdImpression(impressionData: ImpressionData?) {
                }
            })
            show(this@MainActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AdEventBus.clear()
        interstitialAdLoader?.setAdLoadListener(null)
        interstitialAdLoader = null
        destroyInterstitialAd()
    }

    private fun loadInterstitialAd() {
        val adRequestConfiguration =
            AdRequestConfiguration.Builder(BuildConfig.YANDEX_INTERSTITIAL_AD_ID).build()
        interstitialAdLoader?.loadAd(adRequestConfiguration)
    }

    private fun destroyInterstitialAd() {
        interstitialAd?.setAdEventListener(null)
        interstitialAd = null
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        payClient.getIntentInteractor().proceedIntent(intent)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}