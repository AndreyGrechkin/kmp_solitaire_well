package com.defey.solitairewell.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.settings_ads_remove_button_title
import com.defey.solitairewell.resources.settings_ads_title
import com.defey.solitairewell.theme.AppTypography
import com.defey.solitairewell.theme.CardColors
import org.jetbrains.compose.resources.stringResource

@Composable
fun AdsSetting(onClick: () -> Unit) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = stringResource(Res.string.settings_ads_title),
            modifier = Modifier,
            style = AppTypography.headlineMedium
        )
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(color = CardColors.cardFront)
                .border(
                    width = 1.dp,
                    color = CardColors.black,
                    shape = MaterialTheme.shapes.medium
                )
                .clickable(onClick = onClick)
        ) {
            Text(
                text = stringResource(Res.string.settings_ads_remove_button_title),
                modifier = Modifier.padding(8.dp),
                style = AppTypography.headlineSmall
            )
        }
    }
}