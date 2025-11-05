package com.defey.solitairewell

import com.defey.solitairewell.managers.analytics.AnalyticsManager
import com.defey.solitairewell.logic.CommonTimer
import com.defey.solitairewell.logic.TimerFactory
import com.defey.solitairewell.managers.LanguageManager
import com.defey.solitairewell.managers.LanguageManagerImpl
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

expect val adsModule: Module

val commonMonetizationModule = module {

}

internal expect fun platformLanguageModule(): Module