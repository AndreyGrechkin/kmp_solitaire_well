package com.defey.solitairewell.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.ic_cancel_24
import com.defey.solitairewell.resources.ic_check_24
import com.defey.solitairewell.resources.settings_language_dialog_title
import managers.AppLanguage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import com.defey.solitairewell.theme.AppTypography
import com.defey.solitairewell.theme.CardColors

@Composable
fun LanguageDialog(
    currentLanguage: AppLanguage,
    onConfirm: (AppLanguage) -> Unit,
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
            text = stringResource(Res.string.settings_language_dialog_title),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = AppTypography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(AppLanguage.entries) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .clickable { onConfirm(item) }
                ) {
                    Text(
                        text = item.displayName,
                        modifier = Modifier,
                        style = AppTypography.headlineSmall
                    )
                    if (currentLanguage == item) {
                        Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
                        Icon(
                            painter = painterResource(Res.drawable.ic_check_24),
                            contentDescription = "Checked",
                            tint = CardColors.heart,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}