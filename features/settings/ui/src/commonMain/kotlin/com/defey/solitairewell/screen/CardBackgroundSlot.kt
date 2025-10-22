package com.defey.solitairewell.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import factories.BackgroundItem
import rememberCardSize
import theme.CardColors
import toPainter

@Composable
fun CardBackgroundSlot(
    backCard: BackgroundItem,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(rememberCardSize())
            .aspectRatio(0.7f)
            .padding(end = 2.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(color = CardColors.cardFront)
            .border(
                width =  1.dp ,
                color = CardColors.black,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Image(
            painter = backCard.toPainter(),
            contentDescription = "Card back",
            modifier = Modifier.fillMaxSize().padding(4.dp),
        )
    }

}