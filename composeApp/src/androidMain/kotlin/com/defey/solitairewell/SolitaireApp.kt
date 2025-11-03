package com.defey.solitairewell

import android.app.Application
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import com.yandex.mobile.ads.common.MobileAds
import com.yandex.mobile.ads.instream.MobileInstreamAds
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SolitaireApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initializeAppMetrica()
        MobileAds.initialize(this) {
            MobileInstreamAds.setAdGroupPreloading(true)
          if (BuildConfig.DEBUG)
            MobileAds.enableLogging(true)
        }
        startKoin {
            androidContext(this@SolitaireApp)
            modules(
                appModules
            )
        }
    }

    private fun initializeAppMetrica() {
        val config = YandexMetricaConfig.newConfigBuilder(BuildConfig.YANDEX_METRICA)
            .withLogs()
            .withStatisticsSending(true)
            .build()

        YandexMetrica.activate(this, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}