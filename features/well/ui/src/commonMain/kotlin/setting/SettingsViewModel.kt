package setting

import base_viewModel.BaseViewModel
import base.Router
import models.UserData

class SettingsViewModel(
    private val router: Router,
) : BaseViewModel<
        SettingsContract.SettingsEvent,
        SettingsContract.SettingsState,
        SettingsContract.SettingsAction>(
    initialState = SettingsContract.SettingsState()
) {
    override suspend fun handleEvent(event: SettingsContract.SettingsEvent) {
        when (event) {
            is SettingsContract.SettingsEvent.GoBack -> openWell()
        }
    }

    fun openWell() {
        router.navigateBack()
    }

}