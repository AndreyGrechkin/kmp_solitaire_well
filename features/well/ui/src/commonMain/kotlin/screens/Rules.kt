package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RulesContent(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)) {
        Text(
            text = "Правила",
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Это диалог из модуля Well с специфичной логикой")
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onConfirm) {
                Text(text = "OK")
            }
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }

    }
}