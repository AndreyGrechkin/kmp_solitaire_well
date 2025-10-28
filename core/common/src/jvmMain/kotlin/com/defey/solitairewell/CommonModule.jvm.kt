import com.defey.solitairewell.managers.analytics.DesktopAnalytics
import com.defey.solitairewell.managers.analytics.PlatformAnalytics
import managers.JvmLocalization
import managers.Localization
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformLanguageModule(): Module = module {
    single<Localization> { JvmLocalization() }
}

actual val analyticsModule: Module
    get() = module {
        single<PlatformAnalytics> { DesktopAnalytics() }
    }