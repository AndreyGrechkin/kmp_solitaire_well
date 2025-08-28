package setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import models.UserData
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import theme.CardColors

@Composable
fun SettingsScreen() {
//    val vm: SettingsViewModel = koinViewModel(parameters = { parametersOf(userData) })
    val vm: SettingsViewModel = koinViewModel()

    val state by vm.state.collectAsState()
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(CardColors.defaultBackground)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Колода")
                Text("Рубашка")
                Text("Фон")
                Text("Отключение рекламы")

            }
        }
    }
}