import com.defey.solitairewell.managers.analytics.IosAnalytics
import com.defey.solitairewell.managers.analytics.PlatformAnalytics
import managers.IosLocalization
import managers.Localization
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformLanguageModule(): Module = module {
    single<Localization> { IosLocalization() }
}

actual val analyticsModule: Module
    get() = module {
        single<PlatformAnalytics> { IosAnalytics() }
    }