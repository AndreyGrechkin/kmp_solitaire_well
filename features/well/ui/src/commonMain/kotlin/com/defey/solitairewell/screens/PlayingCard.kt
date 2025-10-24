package com.defey.solitairewell.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.defey.solitairewell.factories.CardResource
import com.defey.solitairewell.model.CardState
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import com.defey.solitairewell.theme.CardColors

@Composable
fun PlayingCard(
    cardResource: CardResource,
    backCard: DrawableResource,
    stackSize: Int,
    isFaceUp: Boolean,
    modifier: Modifier = Modifier,
    state: CardState,
    isVisibleCount: Boolean,
    onAnimationComplete: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    AnimatedBorder(
        state = state,
        stackSize = stackSize,
        isVisibleCount = isVisibleCount,
        onAnimationComplete = onAnimationComplete
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .clickable(onClick = onClick)
                .background(color = CardColors.cardFront)
        ) {
            if (isFaceUp) {
                Column(Modifier.fillMaxSize().padding(horizontal = 2.dp)) {
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
                    painter = painterResource(backCard),
                    contentDescription = "Card back",
                    modifier = Modifier.fillMaxSize().padding(4.dp),
                )
            }
        }
    }
}