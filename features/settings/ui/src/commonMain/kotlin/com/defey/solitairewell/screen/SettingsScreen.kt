package com.defey.solitairewell.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.SettingsContract
import com.defey.solitairewell.SettingsViewModel
import com.defey.solitairewell.toPainter
import com.defey.solitairewell.dialog.CustomDialog
import com.defey.solitairewell.dialog.rememberDialogController
import com.defey.solitairewell.factories.CardResourcesFactory
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = koinViewModel()
    val cardFactory = koinInject<CardResourcesFactory>()
    val dialogController = rememberDialogController()
    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsState()

    key(state.currentLanguage) {
        Scaffold(
            topBar = {
                SettingNavBar {
                    viewModel.onEvent(SettingsContract.SettingsEvent.GoBack)
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Image(
                    painter = cardFactory.backGround[state.backgroundItemIndex].toPainter(),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                ) {

                    CardFaceSetting(
                        cardFactory = cardFactory,
                        dialogController = dialogController,
                        deck = state.deck
                    ) {
                        viewModel.onEvent(SettingsContract.SettingsEvent.SaveDeck(it))
                    }

                    CardBackSetting(
                        cardFactory = cardFactory,
                        dialogController = dialogController,
                        backCardIndex = state.backCardIndex
                    ) {
                        viewModel.onEvent(SettingsContract.SettingsEvent.SaveBackCard(it))
                    }

                    BackgroundSetting(
                        cardFactory = cardFactory,
                        dialogController = dialogController,
                        backgroundItemIndex = state.backgroundItemIndex
                    ) {
                        viewModel.onEvent(SettingsContract.SettingsEvent.SaveBackgroundItem(it))
                    }

                    LanguageSetting(
                        dialogController = dialogController,
                        language = state.currentLanguage
                    ) {
                        viewModel.onEvent(SettingsContract.SettingsEvent.SaveLanguage(it))
                    }
                }
            }
            CustomDialog(
                controller = dialogController,
                modifier = Modifier.padding(48.dp)
            )
        }
    }
}