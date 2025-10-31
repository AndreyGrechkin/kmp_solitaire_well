package com.defey.solitairewell

import com.defey.solitairewell.managers.analytics.AndroidAnalytics
import com.defey.solitairewell.managers.analytics.PlatformAnalytics
import com.defey.solitairewell.managers.update.RuStoreUpdateManager
import com.defey.solitairewell.managers.update.UpdateManager
import com.defey.solitairewell.managers.AndroidLocalization
import com.defey.solitairewell.managers.Localization
import com.defey.solitairewell.managers.ads.AdConfig
import com.defey.solitairewell.managers.ads.AdManager
import com.defey.solitairewell.managers.ads.VkAdManager
import com.defey.solitairewell.managers.billing.PurchaseManager
import managers.AndroidLocalization
import managers.Localization
import org.koin.android.ext.koin.androidContext
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

actual val updateModule: Module
    get() = module {
        single<UpdateManager> { RuStoreUpdateManager(get()) }
    }

actual val monetizationModule: Module = module {
    includes(commonMonetizationModule)

    single<AdConfig> { AdConfig() }

    // ✅ AdManager для Android (VK Ads)
    single<AdManager> {
        VkAdManager(androidContext())
    }

    // ✅ PurchaseManager для Android (RuStore)
//    single<PurchaseManager> {
//        RuStorePurchaseManager(androidContext())
//    }
}