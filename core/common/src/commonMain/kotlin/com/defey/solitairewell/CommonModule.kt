import com.defey.solitairewell.managers.ads.AdConfig
import com.defey.solitairewell.managers.analytics.AnalyticsManager
import logic.CommonTimer
import logic.TimerFactory
import managers.LanguageManager
import managers.LanguageManagerImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val commonModule
    get() = module {
        factory<CommonTimer> { TimerFactory.create() }
        single<LanguageManager> { LanguageManagerImpl(get(), get()) }
    }

val languageModule
    get() = module {
        includes(platformLanguageModule())
    }

expect val monetizationModule: Module

/**
 * ✅ Общие зависимости для монетизации
 */
val commonMonetizationModule = module {
    // ✅ AdConfig - единый для всех платформ

    // ✅ AdCreditSystem - система кредитов
//    single { AdCreditSystem() }

    // ✅ AnalyticsManager - аналитика
    single { AnalyticsManager() }
}

internal expect fun platformLanguageModule(): Module