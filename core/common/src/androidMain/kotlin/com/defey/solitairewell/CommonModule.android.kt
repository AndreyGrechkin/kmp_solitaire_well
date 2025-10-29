package com.defey.solitairewell

import com.defey.solitairewell.managers.analytics.AndroidAnalytics
import com.defey.solitairewell.managers.analytics.PlatformAnalytics
import com.defey.solitairewell.managers.update.RuStoreUpdateManager
import com.defey.solitairewell.managers.update.UpdateManager
import com.defey.solitairewell.managers.AndroidLocalization
import com.defey.solitairewell.managers.Localization
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