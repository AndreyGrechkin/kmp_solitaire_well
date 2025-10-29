package com.defey.solitairewell.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.settings_face_card_title
import com.defey.solitairewell.dialog.DialogController
import com.defey.solitairewell.factories.CardResourcesFactory
import com.defey.solitairewell.models.Deck
import org.jetbrains.compose.resources.stringResource
import com.defey.solitairewell.theme.AppTypography

@Composable
fun CardFaceSetting(
    cardFactory: CardResourcesFactory,
    dialogController: DialogController,
    deck: Deck,
    onClick: (Deck) -> Unit
) {
    Column {
        Text(
            text = stringResource(Res.string.settings_face_card_title),
            modifier = Modifier.padding(top = 16.dp),
            style = AppTypography.headlineMedium,
        )

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clickable(
                    onClick = {
                        dialogController.showDialog {
                            CardFaceDialog(
                                cardFactory = cardFactory,
                                selectedDeck = deck,
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
        ) {
            CardFaceRow(cardFactory, deck)
        }
    }
}