import models.UserData
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import screens.WellViewModel
import setting.SettingsViewModel

val wellModule
    get() = module {
        viewModel { WellViewModel(get()) }
        viewModel { (userData: UserData) -> SettingsViewModel(userData, get()) }
    }