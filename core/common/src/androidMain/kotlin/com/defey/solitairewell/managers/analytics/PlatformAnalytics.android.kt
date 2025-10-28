@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.defey.solitairewell.managers.analytics

import com.yandex.metrica.YandexMetrica

class AndroidAnalytics() : PlatformAnalytics {

    override fun logEvent(event: String, params: Map<String, Any>) {
        YandexMetrica.reportEvent(event, params)
    }
}