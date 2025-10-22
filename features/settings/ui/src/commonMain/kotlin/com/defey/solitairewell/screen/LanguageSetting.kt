package com.defey.solitairewell.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.defey.solitairewell.SettingsContract
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.settings_language_title
import dialog.DialogController
import factories.CardResourcesFactory
import managers.AppLanguage
import org.jetbrains.compose.resources.stringResource
import rememberCardSize
import theme.AppTypography
import theme.CardColors

@Composable
fun LanguageSetting(
    dialogController: DialogController,
    language: AppLanguage,
    onClick: (AppLanguage) -> Unit
) {
    Column {
        Text(
            text = stringResource(Res.string.settings_language_title),
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
                .clickable(
                    onClick = {
                        dialogController.showDialog {
                            LanguageDialog(
                                currentLanguage = language,
                                onConfirm = {
                                    onClick(it)
                                    dialogController.hideDialog()
                                },
                                onDismiss = {
                                    dialogController.hideDialog()
                                }
                            )
                        }
                    }
                )
        ) {
            Text(
                text = language.displayName,
                modifier = Modifier.padding(8.dp),
                style = AppTypography.headlineSmall
            )
        }
    }
}