package com.defey.solitairewell

import com.defey.solitairewell.managers.analytics.IosAnalytics
import com.defey.solitairewell.managers.analytics.PlatformAnalytics
import com.defey.solitairewell.managers.update.IosUpdateManager
import com.defey.solitairewell.managers.update.UpdateManager
import com.defey.solitairewell.managers.ads.AdConfig
import com.defey.solitairewell.managers.ads.AdManager
import com.defey.solitairewell.managers.ads.MockAdManager
import com.defey.solitairewell.managers.billing.PurchaseManager
import managers.IosLocalization
import com.defey.solitairewell.managers.Localization
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformLanguageModule(): Module = module {
    single<Localization> { IosLocalization() }
}

actual val analyticsModule: Module
    get() = module {
        single<PlatformAnalytics> { IosAnalytics() }
    }

actual val updateModule: Module
    get() = module {
        single<UpdateManager> { IosUpdateManager() }
    }

actual val monetizationModule: Module = module {
    includes(commonMonetizationModule)

    single<AdConfig> { AdConfig() }

    // ✅ AdManager для Desktop (заглушка)
    single<AdManager> {
        MockAdManager()
    }

    // ✅ PurchaseManager для Desktop (заглушка)
//    single<PurchaseManager> {
//        MockPurchaseManager()
//    }
}