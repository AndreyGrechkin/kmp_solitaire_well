package com.defey.solitairewell.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.well_rules_close_button_title
import com.defey.solitairewell.resources.well_rules_deck_first
import com.defey.solitairewell.resources.well_rules_deck_second
import com.defey.solitairewell.resources.well_rules_deck_third
import com.defey.solitairewell.resources.well_rules_deck_title
import com.defey.solitairewell.resources.well_rules_end_game_lose
import com.defey.solitairewell.resources.well_rules_end_game_title
import com.defey.solitairewell.resources.well_rules_end_game_win
import com.defey.solitairewell.resources.well_rules_how_playing_build_first
import com.defey.solitairewell.resources.well_rules_how_playing_build_second
import com.defey.solitairewell.resources.well_rules_how_playing_build_third
import com.defey.solitairewell.resources.well_rules_how_playing_build_title
import com.defey.solitairewell.resources.well_rules_how_playing_title
import com.defey.solitairewell.resources.well_rules_moved_wells_first
import com.defey.solitairewell.resources.well_rules_moved_wells_second
import com.defey.solitairewell.resources.well_rules_moved_wells_title
import com.defey.solitairewell.resources.well_rules_purpose_game_desc
import com.defey.solitairewell.resources.well_rules_purpose_game_title
import com.defey.solitairewell.resources.well_rules_title
import com.defey.solitairewell.resources.well_rules_waste_first
import com.defey.solitairewell.resources.well_rules_waste_second
import com.defey.solitairewell.resources.well_rules_waste_title
import org.jetbrains.compose.resources.stringResource
import com.defey.solitairewell.theme.AppTypography
import com.defey.solitairewell.theme.CardColors

@Composable
fun RulesContent(
    onConfirm: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(Res.string.well_rules_title),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            style = AppTypography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.well_rules_purpose_game_title),
            style = AppTypography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.well_rules_purpose_game_desc),
            style = AppTypography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.well_rules_how_playing_title),
            style = AppTypography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.well_rules_how_playing_build_title),
            style = AppTypography.titleSmall
        )
        Text(
            text = stringResource(Res.string.well_rules_how_playing_build_first),
            style = AppTypography.bodyMedium
        )
        Text(
            text = stringResource(Res.string.well_rules_how_playing_build_second),
            style = AppTypography.bodyMedium
        )
        Text(
            text = stringResource(Res.string.well_rules_how_playing_build_third),
            style = AppTypography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.well_rules_waste_title),
            style = AppTypography.titleSmall
        )
        Text(
            text = stringResource(Res.string.well_rules_waste_first),
            style = AppTypography.bodyMedium
        )
        Text(
            text = stringResource(Res.string.well_rules_waste_second),
            style = AppTypography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.well_rules_moved_wells_title),
            style = AppTypography.titleSmall
        )
        Text(
            text = stringResource(Res.string.well_rules_moved_wells_first),
            style = AppTypography.bodyMedium
        )
        Text(
            text = stringResource(Res.string.well_rules_moved_wells_second),
            style = AppTypography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.well_rules_deck_title),
            style = AppTypography.titleSmall
        )
        Text(
            text = stringResource(Res.string.well_rules_deck_first),
            style = AppTypography.bodyMedium
        )
        Text(
            text = stringResource(Res.string.well_rules_deck_second),
            style = AppTypography.bodyMedium
        )
        Text(
            text = stringResource(Res.string.well_rules_deck_third),
            style = AppTypography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.well_rules_end_game_title),
            style = AppTypography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.well_rules_end_game_win),
            style = AppTypography.bodyMedium
        )
        Text(
            text = stringResource(Res.string.well_rules_end_game_lose),
            style = AppTypography.bodyMedium
        )


        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CardColors.containerButtonColor,
                    contentColor = CardColors.contentButtonColor
                )
            ) {
                Text(
                    text = stringResource(Res.string.well_rules_close_button_title),
                    style = AppTypography.titleSmall
                )
            }
        }
    }
}