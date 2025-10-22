import managers.JvmLocalization
import managers.Localization
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformLanguageModule(): Module = module {
    single<Localization> { JvmLocalization() }
}