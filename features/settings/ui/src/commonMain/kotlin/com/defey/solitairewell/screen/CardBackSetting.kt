package com.defey.solitairewell.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.settings_back_card_title
import dialog.DialogController
import factories.CardResourcesFactory
import org.jetbrains.compose.resources.stringResource
import theme.AppTypography

@Composable
fun CardBackSetting(
    cardFactory: CardResourcesFactory,
    dialogController: DialogController,
    backCardIndex: Int,
    onClick: (Int) -> Unit
) {
    Column {
        Text(
            text = stringResource(Res.string.settings_back_card_title),
            modifier = Modifier,
            style = AppTypography.headlineMedium
        )

        CardBackSlot(
            backCard = cardFactory.createBackCard(backCardIndex),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clickable(
                    onClick = {
                        dialogController.showDialog {
                            BackCardDialog(
                                cardFactory = cardFactory,
                                selectedBack = backCardIndex,
                                onConfirm = {
                                    dialogController.hideDialog()
                                    onClick(it)
                                },
                                onDismiss = {
                                    dialogController.hideDialog()
                                }
                            )
                        }
                    }
                )
        )
    }
}