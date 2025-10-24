package com.defey.solitairewell.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.ic_cancel_24
import com.defey.solitairewell.resources.settings_face_card_dialog_title
import com.defey.solitairewell.factories.CardResourcesFactory
import models.Deck
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import com.defey.solitairewell.theme.AppTypography
import com.defey.solitairewell.theme.CardColors

@Composable
fun CardFaceDialog(
    cardFactory: CardResourcesFactory,
    selectedDeck: Deck? = null,
    onConfirm: (Deck) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_cancel_24),
            contentDescription = "Close",
            tint = CardColors.heart,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.End)
                .clickable(onClick = onDismiss),
        )
        Text(
            text = stringResource(Res.string.settings_face_card_dialog_title),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = AppTypography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(Deck.entries) { deck ->
                val isSelected = deck == selectedDeck
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onConfirm(deck) }
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth().align(Alignment.Center),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = if (isSelected) 0.dp else 4.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) CardColors.selectedDialog
                            else CardColors.cardFront
                        )
                    ) {
                        Box(
                            modifier = Modifier.padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CardFaceRow(
                                cardFactory = cardFactory,
                                deck = deck,
                                horizontalArrangement = Arrangement.SpaceBetween
                            )
                        }
                    }
                }
            }
        }
    }
}