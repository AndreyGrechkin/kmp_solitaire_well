package com.defey.solitairewell.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.ic_cancel_24
import com.defey.solitairewell.resources.settings_background_dialog_title
import factories.CardResourcesFactory
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import rememberCardSize
import theme.AppTypography
import theme.CardColors
import toPainter

@Composable
fun BackgroundDialog(
    cardFactory: CardResourcesFactory,
    selectedBack: Int? = null,
    modifier: Modifier = Modifier,
    onConfirm: (Int) -> Unit,
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
                .clickable(onClick = onDismiss)
        )
        Text(
            text = stringResource(Res.string.settings_background_dialog_title),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = AppTypography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(cardFactory.backGround) { index, item ->
                val isSelected = index == selectedBack
                Box(
                    modifier = modifier
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                        .padding(end = 2.dp)
                        .clickable { onConfirm(index) }
                        .clip(MaterialTheme.shapes.medium)
                        .background(color = CardColors.cardFront)
                        .border(
                            width = 1.dp,
                            color = CardColors.black,
                            shape = MaterialTheme.shapes.medium
                        )
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
                            modifier = Modifier.padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = item.toPainter(),
                                contentDescription = "Card back",
                                modifier = Modifier.fillMaxSize().padding(0.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}