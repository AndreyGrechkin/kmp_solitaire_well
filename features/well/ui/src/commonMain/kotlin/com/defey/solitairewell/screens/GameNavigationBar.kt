package com.defey.solitairewell.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.WellMenu
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.back_move
import com.defey.solitairewell.resources.game_list
import com.defey.solitairewell.resources.help
import com.defey.solitairewell.resources.new_game
import com.defey.solitairewell.resources.rules
import com.defey.solitairewell.resources.settings
import com.defey.solitairewell.statusBarPadding
import org.jetbrains.compose.resources.painterResource

@Composable
fun GameNavigationBar(
    availableBackMove: Boolean,
    availableHint: Boolean,
    onClickMenu: (WellMenu) -> Unit,
) {
    Row(
        modifier = Modifier
            .statusBarPadding()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            painter = painterResource(Res.drawable.new_game),
            contentDescription = "New game",
            modifier = Modifier
                .size(48.dp)
                .clickable(onClick = { onClickMenu(WellMenu.NEW_GAME) }),
        )
        Image(
            painter = painterResource(Res.drawable.back_move),
            contentDescription = "Back move",
            colorFilter = if (availableBackMove) null else ColorFilter.tint(
                Color.Gray,
                blendMode = BlendMode.Modulate
            ),
            modifier = Modifier
                .size(48.dp)
                .clickable(
                    enabled = availableBackMove,
                    onClick = { onClickMenu(WellMenu.BACK_MOVE) }
                ),
        )

        Image(
            painter = painterResource(Res.drawable.help),
            contentDescription = "Help",
            colorFilter = if (availableHint) null else ColorFilter.tint(
                Color.Gray,
                blendMode = BlendMode.Modulate
            ),
            modifier = Modifier
                .size(48.dp)
                .clickable(
                    enabled = availableHint,
                    onClick = { onClickMenu(WellMenu.HELP) }
                ),
        )

        Image(
            painter = painterResource(Res.drawable.settings),
            contentDescription = "Settings",
            modifier = Modifier
                .size(48.dp)
                .clickable(onClick = { onClickMenu(WellMenu.SETTINGS) }),
        )

        Image(
            painter = painterResource(Res.drawable.rules),
            contentDescription = "Rules",
            modifier = Modifier
                .size(48.dp)
                .clickable(onClick = { onClickMenu(WellMenu.RULES) }),
        )

        Image(
            painter = painterResource(Res.drawable.game_list),
            contentDescription = "List games",
            modifier = Modifier
                .size(48.dp)
                .clickable(onClick = { onClickMenu(WellMenu.OTHER_GAMES) }),
        )
    }
}