import com.defey.solitairewell.managers.ads.AdConfig
import com.defey.solitairewell.managers.ads.AdManager
import com.defey.solitairewell.managers.ads.MockAdManager
import com.defey.solitairewell.managers.billing.PurchaseManager
import managers.IosLocalization
import managers.Localization
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformLanguageModule(): Module = module {
    single<Localization> { IosLocalization() }
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