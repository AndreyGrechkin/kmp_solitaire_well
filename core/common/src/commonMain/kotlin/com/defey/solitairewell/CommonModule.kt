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

expect val analyticsModule: Module

val commonAnalyticsModule
    get() = module {
        includes(analyticsModule)
        single<AnalyticsManager> { AnalyticsManager(get()) }
    }

internal expect fun platformLanguageModule(): Module