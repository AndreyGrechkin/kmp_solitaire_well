package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import SettingsViewModel

val settingsModule
    get() = module {
        includes(settingsDomainModule)
        viewModel { SettingsViewModel(get(), get()) }
    }