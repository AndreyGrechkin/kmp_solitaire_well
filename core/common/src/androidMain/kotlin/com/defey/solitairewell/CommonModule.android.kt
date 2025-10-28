import com.defey.solitairewell.managers.analytics.AndroidAnalytics
import com.defey.solitairewell.managers.analytics.PlatformAnalytics
import managers.AndroidLocalization
import managers.Localization
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