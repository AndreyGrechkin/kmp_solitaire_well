package com.defey.solitairewell

import android.app.Application
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SolitaireApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initializeAppMetrica()
        startKoin {
            androidContext(this@SolitaireApp)
            modules(
                appModules
            )
        }
    }

    private fun initializeAppMetrica() {
        val config = YandexMetricaConfig.newConfigBuilder("b1e1ff7f-89ac-4090-b69d-3a671a4adb69")
            .withLogs()
            .withStatisticsSending(true)
            .build()

        YandexMetrica.activate(this, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}