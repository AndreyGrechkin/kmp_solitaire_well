package com.defey.solitairewell

import com.defey.solitairewell.managers.AndroidLocalization
import com.defey.solitairewell.managers.Localization
import com.defey.solitairewell.managers.ads.AdManager
import com.defey.solitairewell.managers.ads.AdManagerImpl
import com.defey.solitairewell.managers.analytics.AndroidAnalytics
import com.defey.solitairewell.managers.analytics.PlatformAnalytics
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformLanguageModule(): Module =
    module {
        single<Localization> { AndroidLocalization(get()) }
    }

actual val analyticsModule: Module
    get() = module {
        single<PlatformAnalytics> { AndroidAnalytics() }
    }

actual val adsModule: Module = module {
    includes(commonMonetizationModule)
    single<AdManager> { AdManagerImpl() }
}