package com.defey.solitairewell.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.defey.solitairewell.LocalOrientationController
import com.defey.solitairewell.WellContract
import com.defey.solitairewell.WellContract.WellEvent.OnAnimationFinished
import com.defey.solitairewell.WellContract.WellEvent.OnClickCard
import com.defey.solitairewell.WellContract.WellEvent.OnLoadGame
import com.defey.solitairewell.WellContract.WellEvent.OnMenu
import com.defey.solitairewell.WellMenu
import com.defey.solitairewell.WellViewModel
import com.defey.solitairewell.ads.BannerAd
import com.defey.solitairewell.dialog.CustomDialog
import com.defey.solitairewell.dialog.rememberDialogController
import com.defey.solitairewell.factories.CardResourcesFactory
import com.defey.solitairewell.toPainter
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WellScreen() {
    val viewModel: WellViewModel = koinViewModel()
    val cardFactory = koinInject<CardResourcesFactory>()
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val dialogController = rememberDialogController()
    val orientationController = LocalOrientationController.current
    orientationController.LockPortraitOrientation()

    LaunchedEffect(Unit) {
        viewModel.action.collect { action ->
            when (action) {
                WellContract.WellAction.ShowRenewalDialog -> {
                    scope.launch {

                        dialogController.showDialog {
                            ReGameDialog(
                                dialogController = dialogController,
                                onRestart = {
                                    viewModel.onEvent(OnLoadGame)
                                },
                                onReset = {
                                    viewModel.onEvent(OnMenu(WellMenu.NEW_GAME))
                                }
                            )
                        }
                    }
                }

                WellContract.WellAction.ShowRulesDialog -> {
                    dialogController.showDialog {
                        RulesContent(
                            onConfirm = {
                                dialogController.hideDialog()
                            }
                        )
                    }
                }

                is WellContract.WellAction.ShowWinDialog -> {
                    dialogController.showDialog {
                        GameFinishAnimation(action.status) {
                            viewModel.onEvent(WellContract.WellEvent.OnFinishGame)
                            dialogController.hideDialog()
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            GameNavigationBar(
                availableBackMove = state.availableBackMove,
                availableHint = state.availableHint
            ) {
                viewModel.onEvent(OnMenu(it))
            }
        },
        bottomBar = { BannerAd() }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = cardFactory.backGround[state.backgroundItemIndex].toPainter(),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(16.dp),
            ) {

                ActiveCardsStackRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    stackWells = state.stackWells,
                    cardFactory = cardFactory,
                    deck = state.deck,
                    gameState = state.gameState,
                    helpState = state.hintState,
                    backCardIndex = state.backCardIndex,
                    onAnimationComplete = {
                        viewModel.onEvent(OnAnimationFinished)
                    },
                    onClick = { state ->
                        viewModel.onEvent(OnClickCard(state = state))
                    }
                )

                Box {
                    CardsStackRow(
                        stackWells = state.stackWells,
                        cardFactory = cardFactory,
                        gameState = state.gameState,
                        helpState = state.hintState,
                        step = state.gameStep,
                        backCardIndex = state.backCardIndex,
                        deck = state.deck,
                        onAnimationComplete = {
                            viewModel.onEvent(OnAnimationFinished)
                        },
                        onClick = { state ->
                            viewModel.onEvent(OnClickCard(state = state))
                        }
                    )
                    WellSlotBox(
                        stackWells = state.stackWells,
                        cardFactory = cardFactory,
                        backCardIndex = state.backCardIndex,
                        deck = state.deck,
                        gameState = state.gameState,
                        helpState = state.hintState,
                        onAnimationComplete = {
                            viewModel.onEvent(OnAnimationFinished)
                        },
                        onClick = { state ->
                            viewModel.onEvent(OnClickCard(state = state))
                        }
                    )
                }
            }
        }
        CustomDialog(
            controller = dialogController,
            modifier = Modifier.padding(48.dp)
        )
    }
}