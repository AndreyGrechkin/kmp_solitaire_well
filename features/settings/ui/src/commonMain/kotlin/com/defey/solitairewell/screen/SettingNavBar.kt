package com.defey.solitairewell.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.back_move
import com.defey.solitairewell.resources.settings_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import statusBarPadding
import theme.AppTypography

@Composable
fun SettingNavBar(
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .statusBarPadding()
            .padding(horizontal = 16.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.back_move),
            contentDescription = "navigate back",
            modifier = Modifier
                .size(48.dp)
                .clickable(onClick = onBack)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),

            textAlign = TextAlign.Center,
            text = stringResource(Res.string.settings_title),
            style = AppTypography.headlineLarge
        )
    }
}