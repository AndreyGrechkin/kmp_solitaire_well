package com.defey.solitairewell.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.defey.solitairewell.resources.BC_1
import com.defey.solitairewell.resources.Res
import factories.CardResource
import org.jetbrains.compose.resources.painterResource
import rememberCardSize
import theme.CardColors

@Composable
fun CardRankSlot(
    cardResource: CardResource,
    isFaceUp: Boolean,
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
        if (isFaceUp) {
            Column(Modifier.fillMaxSize()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = cardResource.rank,
                        color = CardColors.black,
                        fontSize = 32.sp
                    )
                    Image(
                        painter = painterResource(cardResource.suit),
                        contentDescription = cardResource.rank,
                        modifier = Modifier.size(24.dp),
                    )
                }
                Image(
                    painter = painterResource(cardResource.image),
                    contentDescription = cardResource.rank,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        } else {
            Image(
                painter = painterResource(Res.drawable.BC_1),
                contentDescription = "Card back",
                modifier = Modifier.fillMaxSize().padding(4.dp),
            )
        }
    }
}