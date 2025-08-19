package setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import models.UserData
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SettingsScreen(userData: UserData) {
    val vm: SettingsViewModel = koinViewModel(parameters = { parametersOf(userData) })
    val state by vm.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Добро пожаловать, ${state.userData.username}!")
        Text("Ваш счет: ${state.userData.score}")
        Button(onClick = { vm.onEvent(SettingsContract.SettingsEvent.GoBack) }) {
            Text("Назад")
        }
    }
}