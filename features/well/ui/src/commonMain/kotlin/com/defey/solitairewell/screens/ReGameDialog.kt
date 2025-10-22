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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.well_restore_dialog_message
import com.defey.solitairewell.resources.well_restore_dialog_new_game_button_title
import com.defey.solitairewell.resources.well_restore_dialog_restore_button_title
import com.defey.solitairewell.resources.well_restore_dialog_title
import dialog.DialogController
import org.jetbrains.compose.resources.stringResource
import theme.AppTypography
import theme.CardColors

@Composable
fun ReGameDialog(
    dialogController: DialogController,
    onRestart: () -> Unit,
    onReset: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(Res.string.well_restore_dialog_title),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            style = AppTypography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.well_restore_dialog_message),
            style = AppTypography.bodyMedium
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CardColors.containerButtonColor,
                    contentColor = CardColors.contentButtonColor
                ),
                contentPadding = PaddingValues(horizontal = 2.dp),
                onClick = {
                    onRestart()
                    dialogController.hideDialog()
                }) {
                Text(
                    text = stringResource(Res.string.well_restore_dialog_restore_button_title),
                    style = AppTypography.titleSmall
                )
            }
            Button(
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CardColors.containerButtonColor,
                    contentColor = CardColors.contentButtonColor
                ),
                contentPadding = PaddingValues(horizontal = 2.dp),
                onClick = {
                    onReset()
                    dialogController.hideDialog()
                }
            ) {
                Text(
                    text = stringResource(Res.string.well_restore_dialog_new_game_button_title),
                    style = AppTypography.titleSmall
                )
            }
        }
    }
}