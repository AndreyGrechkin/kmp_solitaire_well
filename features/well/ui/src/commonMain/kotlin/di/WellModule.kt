package di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import WellViewModel

val wellModule
    get() = module {
        includes(wellDomainModule)
        viewModel { WellViewModel(get(), get(), get(), get(), get()) }
//        viewModel { SettingsViewModel(get(), get()) }
    }