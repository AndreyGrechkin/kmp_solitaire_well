import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformStorageModule(): Module = module {
    single<KeyValueStorage> { AndroidKeyValueStorage(get()) }
//    single { AppSettings(get()) }
}