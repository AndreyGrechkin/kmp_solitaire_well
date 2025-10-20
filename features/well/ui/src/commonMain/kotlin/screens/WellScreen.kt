package screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.defey.solitairewell.resources.Res
import com.defey.solitairewell.resources.*
import debugLog
import dialog.CustomDialog
import dialog.rememberDialogController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import factories.CardResourcesFactory
import models.WellCardStack
import model.CardState
import model.GameState
import models.WellSlotAddress
import models.WellSlotType
import factories.CardResource
import models.Deck
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import rememberCardSize
import screens.WellViewModel.Companion.BOTTOM_INDEX
import screens.WellViewModel.Companion.LEFT_INDEX
import screens.WellViewModel.Companion.RIGHT_INDEX
import screens.WellViewModel.Companion.TOP_INDEX
import theme.CardColors

@Composable
fun WellScreen() {
    val viewModel: WellViewModel = koinViewModel()
    val cardFactory = koinInject<CardResourcesFactory>()
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
//    val action by viewModel.action.collectAsState()
    val scrollState = rememberScrollState()
    val dialogController = rememberDialogController()

    // Собираем действия из ViewModel
    LaunchedEffect(Unit) {
        viewModel.action.collect { action ->
            when (action) {
                WellContract.WellAction.ShowRenewalDialog -> {
                    // Показываем диалог при получении действия
                    scope.launch {
                        // Показываем диалог с кастомным содержимым
                        dialogController.showDialog {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)) {
                                Text(
                                    text = "Старая игра",
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally),
                                    style = MaterialTheme.typography.headlineMedium)
                                Spacer(modifier = Modifier.height(16.dp))

                                Text("Есть не завершеный пасьянс")
                                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Button(
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFFA5D6A7), // светлый зелёный
                                            contentColor = Color(0xFF2E7D32)
                                        ),
                                        contentPadding = PaddingValues(horizontal = 2.dp),
                                        onClick = {
                                        viewModel.onEvent(WellContract.WellEvent.OnLoadGame)
                                        dialogController.hideDialog()
                                    }) {
                                        Text(text = "Возобновить")
                                    }
                                    Button(
                                        modifier = Modifier
                                            .weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFFA5D6A7), // светлый зелёный
                                            contentColor = Color(0xFF2E7D32)
                                        ),
                                        contentPadding = PaddingValues(horizontal = 2.dp),
                                        onClick = {
                                        viewModel.onEvent(WellContract.WellEvent.OnNewGame)
                                        dialogController.hideDialog()
                                    }) {
                                        Text(text = "Новая игра")
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    Scaffold { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(CardColors.defaultBackground)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Image(
                        painter = painterResource(Res.drawable.new_game),
                        contentDescription = "new game",
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                onClick = {
                                    viewModel.onEvent(WellContract.WellEvent.OnNewGame)
                                }
                            ),
                    )
                    Image(
                        painter = painterResource(Res.drawable.back_move),
                        contentDescription = "back",
                        colorFilter = if (state.availableBackMove) null else ColorFilter.tint(
                            Color.Gray,
                            blendMode = BlendMode.Modulate
                        ),
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                enabled = state.availableBackMove,
                                onClick = {
                                    viewModel.onEvent(WellContract.WellEvent.OnBackMove)
                                }
                            ),
                    )

                    Image(
                        painter = painterResource(Res.drawable.help),
                        contentDescription = "new game",
                        colorFilter = if (state.availableHint) null else ColorFilter.tint(
                            Color.Gray,
                            blendMode = BlendMode.Modulate
                        ),
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                enabled = state.availableHint,
                                onClick = {
                                    viewModel.onEvent(WellContract.WellEvent.OnHelp)
                                }
                            ),
                    )

                    Image(
                        painter = painterResource(Res.drawable.settings),
                        contentDescription = "new game",
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                onClick = {
                                    viewModel.onEvent(WellContract.WellEvent.NavigateToSettings)
                                }
                            ),
                    )
                    Image(
                        painter = painterResource(Res.drawable.rules),
                        contentDescription = "rules",
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                onClick = {
                                    // Показываем диалог с кастомным содержимым
                                    dialogController.showDialog {
                                        RulesContent(
                                            onConfirm = {
                                                // Логика подтверждения специфичная для Well модуля
                                                debugLog("onConfirm")
                                                dialogController.hideDialog()
                                            },
                                            onDismiss = {
                                                debugLog("onDismiss")
                                                dialogController.hideDialog() }
                                        )
                                    }
                                }
                            ),
                    )
                    Image(
                        painter = painterResource(Res.drawable.game_list),
                        contentDescription = "new game",
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                onClick = {

                                }
                            ),
                    )
                }

                TopRow(
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
                        viewModel.onEvent(WellContract.WellEvent.OnAnimationFinished)
                    },
                    onClick = { state ->
                        viewModel.onEvent(WellContract.WellEvent.OnClickCard(state = state))
                    }
                )
                Box {
                    MiddleRow(
                        stackWells = state.stackWells,
                        cardFactory = cardFactory,
                        gameState = state.gameState,
                        helpState = state.hintState,
                        message = state.gameMessage,
                        backCardIndex = state.backCardIndex,
                        deck = state.deck,
                        onAnimationComplete = {
                            viewModel.onEvent(WellContract.WellEvent.OnAnimationFinished)
                        },
                        onClick = { state ->
                            viewModel.onEvent(WellContract.WellEvent.OnClickCard(state = state))
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
                            viewModel.onEvent(WellContract.WellEvent.OnAnimationFinished)
                        },
                        onClick = { state ->
                            viewModel.onEvent(WellContract.WellEvent.OnClickCard(state = state))
                        }
                    )
                }


            }
        }
        CustomDialog(controller = dialogController,
            modifier = Modifier.padding(48.dp)
        )
    }

}

@Composable
fun TopRow(
    stackWells: List<WellCardStack>,
    cardFactory: CardResourcesFactory,
    modifier: Modifier,
    gameState: GameState,
    backCardIndex: Int,
    deck: Deck,
    helpState: List<GameState>,
    onAnimationComplete: () -> Unit,
    onClick: (GameState) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(5) { index ->
            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(WellSlotType.STOCK_PLAY, index),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = helpState,
                backCardIndex = backCardIndex,
                deck = deck,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )
        }
    }
}

@Composable
fun MiddleRow(
    stackWells: List<WellCardStack>,
    gameState: GameState,
    helpState: List<GameState>,
    message: String,
    deck: Deck,
    backCardIndex: Int,
    cardFactory: CardResourcesFactory,
    onAnimationComplete: () -> Unit,
    onClick: (GameState) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Колода (слева)
            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(WellSlotType.STOCK),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = emptyList(),
                deck = deck,
                backCardIndex = backCardIndex,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )
            Text(
                text = message,
                color = CardColors.black,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.weight(2f))

        Column {
            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(WellSlotType.WASTE),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = helpState,
                backCardIndex = backCardIndex,
                deck = deck,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )
            Text(
                text = "СКЛАД",
                color = CardColors.black,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

    }
}

@Composable
fun WellSlotBox(
    stackWells: List<WellCardStack>,
    backCardIndex: Int,
    gameState: GameState,
    helpState: List<GameState>,
    cardFactory: CardResourcesFactory,
    deck: Deck,
    onAnimationComplete: () -> Unit,
    onClick: (GameState) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 32.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(WellSlotType.EXTERNAL_WELL, TOP_INDEX),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = helpState,
                backCardIndex = backCardIndex,
                deck = deck,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(WellSlotType.INNER_WELL, TOP_INDEX),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = helpState,
                backCardIndex = backCardIndex,
                deck = deck,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(WellSlotType.INNER_WELL, BOTTOM_INDEX),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = helpState,
                backCardIndex = backCardIndex,
                deck = deck,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = WellSlotAddress(WellSlotType.EXTERNAL_WELL, BOTTOM_INDEX),
                cardFactory = cardFactory,
                gameState = gameState,
                helpState = helpState,
                backCardIndex = backCardIndex,
                deck = deck,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )

        }
        Column(
            modifier = Modifier
                .padding(top = calculateHalfCardHeight()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(WellSlotType.FOUNDATION, LEFT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    helpState = helpState,
                    backCardIndex = backCardIndex,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                )
                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(WellSlotType.FOUNDATION, TOP_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    helpState = helpState,
                    backCardIndex = backCardIndex,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )
            }
            Row() {
                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(WellSlotType.EXTERNAL_WELL, LEFT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    backCardIndex = backCardIndex,
                    helpState = helpState,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )

                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(WellSlotType.INNER_WELL, LEFT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    helpState = helpState,
                    backCardIndex = backCardIndex,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )

                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                )
                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(WellSlotType.INNER_WELL, RIGHT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    helpState = helpState,
                    backCardIndex = backCardIndex,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )

                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(WellSlotType.EXTERNAL_WELL, RIGHT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    helpState = helpState,
                    backCardIndex = backCardIndex,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )

            }
            Row() {
                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(WellSlotType.FOUNDATION, BOTTOM_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    helpState = helpState,
                    backCardIndex = backCardIndex,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                )
                CreateCardSlot(
                    stackList = stackWells,
                    address = WellSlotAddress(WellSlotType.FOUNDATION, RIGHT_INDEX),
                    cardFactory = cardFactory,
                    backCardIndex = backCardIndex,
                    gameState = gameState,
                    helpState = helpState,
                    deck = deck,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )
            }
        }

    }
}

@Composable
fun CreateCardSlot(
    stackList: List<WellCardStack>,
    address: WellSlotAddress,
    backCardIndex: Int,
    cardFactory: CardResourcesFactory,
    gameState: GameState,
    helpState: List<GameState>,
    deck: Deck,
    onAnimationComplete: () -> Unit,
    onClick: (GameState) -> Unit,
) {
    val stack = stackList.find { it.address == address }

    val state = if (helpState.isEmpty()) {
        if (gameState.address == address) gameState.state
        else CardState.DEFAULT
    } else {
        helpState.find { it.address == address }?.state ?: CardState.DEFAULT
    }
    val stackSize = stack?.cards?.size ?: 0

    val isVisibleCount = when (address.type) {
        WellSlotType.FOUNDATION -> true
        WellSlotType.STOCK -> false
        WellSlotType.STOCK_PLAY -> false
        WellSlotType.WASTE -> false
        WellSlotType.INNER_WELL -> true
        WellSlotType.EXTERNAL_WELL -> true
    }

    val backCard = cardFactory.createBackCard(backCardIndex)
    if (stack?.cards.isNullOrEmpty()) {
        EmptyCardSlot(
            state = state,
            stackSize = stackSize,
            onAnimationComplete = onAnimationComplete,
            isVisibleCount = isVisibleCount,
            onClick = {
                onClick(
                    GameState(
                        card = null,
                        address = address,
                        state = state
                    )
                )
            }
        )
    } else {
        val topCard = stack.topCard ?: return
        PlayingCard(
            cardResource = cardFactory.createCardResources(topCard, deck),
            backCard = backCard,
            isFaceUp = topCard.isFaceUp,
            state = state,
            stackSize = stackSize,
            isVisibleCount = isVisibleCount,
            onAnimationComplete = onAnimationComplete,
            onClick = {
                onClick(
                    GameState(
                        card = topCard,
                        address = address,
                        state = state
                    )
                )

            }
        )
    }
}

@Composable
fun EmptyCardSlot(
    modifier: Modifier = Modifier,
    stackSize: Int,
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
                .background(color = CardColors.defaultEmptyCardSlot)
        )
    }

}


@Composable
fun calculateHalfCardHeight(): Dp {
    val cardWidth = rememberCardSize()
    return (cardWidth / 0.7f) / 2
}



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

@Composable
fun AnimatedBorder(
    state: CardState,
    stackSize: Int,
    isVisibleCount: Boolean,
    onAnimationComplete: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    LaunchedEffect(state) {
        if (state == CardState.SUCCESS || state == CardState.ERROR) {
            delay(350)
            onAnimationComplete()
        }
    }
    val borderColor by animateColorAsState(
        targetValue = when (state) {
            CardState.SELECTED -> CardColors.selected
            CardState.SUCCESS -> Color.Green
            CardState.ERROR -> Color.Red
            CardState.HINTED -> Color.Blue
            CardState.DEFAULT -> Color.Transparent
        },
        animationSpec = when (state) {
            CardState.SUCCESS,
            CardState.ERROR,
                -> tween(durationMillis = 300)

            else -> tween(durationMillis = 200)
        }
    )

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.0f at 0 using FastOutSlowInEasing
                0.3f at 333 using FastOutSlowInEasing
                1.0f at 666 using FastOutSlowInEasing
            },
            repeatMode = RepeatMode.Restart,
        ),
        label = "blinking_animation"
    )

    val showBlinkingEffect = state == CardState.SUCCESS || state == CardState.ERROR
    val actualBorderColor = if (showBlinkingEffect) {
        borderColor.copy(alpha = alpha)
    } else {
        borderColor
    }
    Column(modifier = Modifier.padding(4.dp)) {
        if (isVisibleCount)
            Text(
                text = stackSize.toString(),
                color = CardColors.black,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

        Box(
            modifier = Modifier
                .width(rememberCardSize())
                .aspectRatio(0.7f)
                .clip(MaterialTheme.shapes.medium)
                .border(
                    width = if (state != CardState.DEFAULT) 2.dp else 0.dp,
                    color = actualBorderColor,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            content()
        }
    }
}


