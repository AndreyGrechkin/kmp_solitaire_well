package com.defey.solitairewell

import com.defey.solitairewell.managers.JvmLocalization
import com.defey.solitairewell.managers.Localization
import com.defey.solitairewell.managers.ads.AdManager
import com.defey.solitairewell.managers.ads.MockAdManager
import com.defey.solitairewell.managers.analytics.DesktopAnalytics
import com.defey.solitairewell.managers.analytics.PlatformAnalytics
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformLanguageModule(): Module = module {
    single<Localization> { JvmLocalization() }
}

actual val analyticsModule: Module
    get() = module {
        single<PlatformAnalytics> { DesktopAnalytics() }
    }

actual val adsModule: Module = module {
    includes(commonMonetizationModule)
    single<AdManager> { MockAdManager() }
}