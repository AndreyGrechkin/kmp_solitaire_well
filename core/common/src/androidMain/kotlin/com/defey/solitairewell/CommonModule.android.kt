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