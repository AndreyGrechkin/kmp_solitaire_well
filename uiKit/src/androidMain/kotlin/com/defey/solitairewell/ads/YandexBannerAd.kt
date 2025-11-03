package com.defey.solitairewell.ads

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.defey.solitairewell.uiKit.BuildConfig
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun YandexBannerAd() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val density = LocalDensity.current
    val screenWidthPx = with(density) { screenWidthDp.dp.toPx() }

    val bannerAd = remember {
        BannerAdView(context).apply {
            setAdSize(
                BannerAdSize.stickySize(
                    context = context,
                    width = screenWidthPx.toInt()
                )
            )
            setAdUnitId(BuildConfig.YANDEX_BANNER_AD_ID)
            setBannerAdEventListener(object : BannerAdEventListener {
                override fun onAdClicked() {

                }

                override fun onAdFailedToLoad(error: AdRequestError) {

                }

                override fun onAdLoaded() {

                }

                override fun onImpression(impressionData: ImpressionData?) {

                }

                override fun onLeftApplication() {

                }

                override fun onReturnedToApplication() {

                }

            })
        }
    }

    AndroidView(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth(),
        factory = { bannerAd }
    )

    DisposableEffect(Unit) {
        bannerAd.loadAd(AdRequest.Builder().build())

        onDispose {
            bannerAd.destroy()
        }
    }
}