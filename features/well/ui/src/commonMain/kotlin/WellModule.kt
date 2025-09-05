import di.wellLogicModule
import models.UserData
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import screens.WellViewModel
import setting.SettingsViewModel

val wellModule
    get() = module {
        includes(wellLogicModule)
        viewModel { WellViewModel(get(), get(), get(), get(), get()) }
        viewModel { SettingsViewModel(get(), get()) }
    }