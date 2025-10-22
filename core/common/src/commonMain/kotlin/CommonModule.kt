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

internal expect fun platformLanguageModule(): Module