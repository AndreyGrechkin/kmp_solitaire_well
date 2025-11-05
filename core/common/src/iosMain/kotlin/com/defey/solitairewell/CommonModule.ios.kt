package com.defey.solitairewell

import com.defey.solitairewell.managers.Localization
import com.defey.solitairewell.managers.ads.AdManager
import com.defey.solitairewell.managers.ads.MockAdManager
import com.defey.solitairewell.managers.analytics.IosAnalytics
import com.defey.solitairewell.managers.analytics.PlatformAnalytics
import managers.IosLocalization
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformLanguageModule(): Module = module {
    single<Localization> { IosLocalization() }
}

actual val analyticsModule: Module
    get() = module {
        single<PlatformAnalytics> { IosAnalytics() }
    }

actual val adsModule: Module = module {
    includes(commonMonetizationModule)
    single<AdManager> { MockAdManager() }
}