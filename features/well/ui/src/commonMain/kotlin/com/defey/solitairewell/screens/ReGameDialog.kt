package com.defey.solitairewell.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dialog.DialogController

@Composable
fun ReGameDialog(
    dialogController: DialogController,
    onRestart: () -> Unit,
    onReset: () -> Unit,
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)) {
        Text(
            text = "Старая игра",
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Есть не завершеный пасьянс")
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA5D6A7), // светлый зелёный
                    contentColor = Color(0xFF2E7D32)
                ),
                contentPadding = PaddingValues(horizontal = 2.dp),
                onClick = {
                    onRestart()
                    dialogController.hideDialog()
                }) {
                Text(text = "Возобновить")
            }
            Button(
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA5D6A7), // светлый зелёный
                    contentColor = Color(0xFF2E7D32)
                ),
                contentPadding = PaddingValues(horizontal = 2.dp),
                onClick = {
                    onReset()
                    dialogController.hideDialog()
                }) {
                Text(text = "Новая игра")
            }
        }
    }
}