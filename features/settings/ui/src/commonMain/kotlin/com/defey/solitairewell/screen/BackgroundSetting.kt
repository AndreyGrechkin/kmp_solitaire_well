package com.defey.solitairewell.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.settings_background_title
import com.defey.solitairewell.dialog.DialogController
import com.defey.solitairewell.factories.CardResourcesFactory
import org.jetbrains.compose.resources.stringResource
import com.defey.solitairewell.theme.AppTypography

@Composable
fun BackgroundSetting(
    cardFactory: CardResourcesFactory,
    dialogController: DialogController,
    backgroundItemIndex: Int,
    onClick: (Int) -> Unit
) {
    Column {
        Text(
            text = stringResource(Res.string.settings_background_title),
            modifier = Modifier,
            style = AppTypography.headlineMedium
        )

        CardBackgroundSlot(
            backCard = cardFactory.createBackGround(backgroundItemIndex),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clickable(
                    onClick = {
                        dialogController.showDialog {
                            BackgroundDialog(
                                cardFactory = cardFactory,
                                selectedBack = backgroundItemIndex,
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