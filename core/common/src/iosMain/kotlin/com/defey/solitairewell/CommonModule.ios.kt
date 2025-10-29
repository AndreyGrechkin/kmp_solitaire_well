package com.defey.solitairewell

import com.defey.solitairewell.managers.analytics.IosAnalytics
import com.defey.solitairewell.managers.analytics.PlatformAnalytics
import com.defey.solitairewell.managers.update.IosUpdateManager
import com.defey.solitairewell.managers.update.UpdateManager
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