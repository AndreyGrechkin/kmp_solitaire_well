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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.defey.solitairewell.data.resources.Res
import com.defey.solitairewell.data.resources.back_blue
import com.defey.solitairewell.data.resources.back_move
import com.defey.solitairewell.data.resources.game_list
import com.defey.solitairewell.data.resources.help
import com.defey.solitairewell.data.resources.new_game
import com.defey.solitairewell.data.resources.settings
import getScreenMetrics
import kotlinx.coroutines.delay
import logic.CardResourcesFactory
import model.CardStack
import model.CardState
import model.GameState
import model.SlotAddress
import model.SlotType
import models.CardResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
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
    val scrollState = rememberScrollState()
    Scaffold { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(CardColors.defaultBackground)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState) // ← Вот и весь секрет!
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
//            Text(text = state.gameMessage, color = CardColors.black, fontSize = 16.sp)
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
                        contentDescription = "new game",
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                onClick = {
                                    viewModel.onEvent(WellContract.WellEvent.OnBackMove)
                                }
                            ),
                    )

                    Image(
                        painter = painterResource(Res.drawable.help),
                        contentDescription = "new game",
                        colorFilter = if (state.isEnabled) null else ColorFilter.tint(Color.Gray , blendMode = BlendMode.Modulate),
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                enabled = state.isEnabled,
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
                    gameState = state.gameState,
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
                        message = state.gameMessage,
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
                        gameState = state.gameState,
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
    }
}

@Composable
fun TopRow(
    stackWells: List<CardStack>,
    cardFactory: CardResourcesFactory,
    modifier: Modifier,
    gameState: GameState,
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
                address = SlotAddress(SlotType.STOCK_PLAY, index),
                cardFactory = cardFactory,
                gameState = gameState,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )
        }
    }
}

@Composable
fun MiddleRow(
    stackWells: List<CardStack>,
    gameState: GameState,
    message: String,
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
                address = SlotAddress(SlotType.STOCK),
                cardFactory = cardFactory,
                gameState = gameState,
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

        // Склад (справа)
        Column {
            CreateCardSlot(
                stackList = stackWells,
                address = SlotAddress(SlotType.WASTE),
                cardFactory = cardFactory,
                gameState = gameState,
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
    stackWells: List<CardStack>,
    gameState: GameState,
    cardFactory: CardResourcesFactory,
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
                address = SlotAddress(SlotType.EXTERNAL_WELL, TOP_INDEX),
                cardFactory = cardFactory,
                gameState = gameState,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = SlotAddress(SlotType.INNER_WELL, TOP_INDEX),
                cardFactory = cardFactory,
                gameState = gameState,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = SlotAddress(SlotType.INNER_WELL, BOTTOM_INDEX),
                cardFactory = cardFactory,
                gameState = gameState,
                onAnimationComplete = onAnimationComplete,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = SlotAddress(SlotType.EXTERNAL_WELL, BOTTOM_INDEX),
                cardFactory = cardFactory,
                gameState = gameState,
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
                    address = SlotAddress(SlotType.FOUNDATION, LEFT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
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
                    address = SlotAddress(SlotType.FOUNDATION, TOP_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )
            }
            Row() {
                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.EXTERNAL_WELL, LEFT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )

                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.INNER_WELL, LEFT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
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
                    address = SlotAddress(SlotType.INNER_WELL, RIGHT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )

                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.EXTERNAL_WELL, RIGHT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )

            }
            Row() {
                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.FOUNDATION, BOTTOM_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
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
                    address = SlotAddress(SlotType.FOUNDATION, RIGHT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    onAnimationComplete = onAnimationComplete,
                    onClick = onClick
                )
            }
        }

    }
}

@Composable
fun CreateCardSlot(
    stackList: List<CardStack>,
    address: SlotAddress,
    cardFactory: CardResourcesFactory,
    gameState: GameState,
    onAnimationComplete: () -> Unit,
    onClick: (GameState) -> Unit,
) {
    val stack = stackList.find { it.address == address }
    val state = if (gameState.address == address) gameState.state
    else CardState.DEFAULT
    val stackSize = stack?.cards?.size ?: 0

    val isVisibleCount = when (address.type) {
        SlotType.FOUNDATION -> true
        SlotType.STOCK -> false
        SlotType.STOCK_PLAY -> false
        SlotType.WASTE -> false
        SlotType.INNER_WELL -> true
        SlotType.EXTERNAL_WELL -> true
    }
    if (stack?.cards.isNullOrEmpty()) {
        EmptyCardSlot(
            state = state,
            stackSize = stackSize,
            onAnimationComplete = onAnimationComplete,
            isVisibleCount = isVisibleCount,
            onClick = {
                onClick(
                    GameState(
                        cards = null,
                        address = address,
                        state = state
                    )
                )
            }
        )
    } else {
        val topCard = stack.topCard ?: return
        PlayingCard(
            cardResource = cardFactory.createCardResources(topCard),
            isFaceUp = topCard.isFaceUp,
            state = state,
            stackSize = stackSize,
            isVisibleCount = isVisibleCount,
            onAnimationComplete = onAnimationComplete,
            onClick = {
                onClick(
                    GameState(
                        cards = topCard,
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
fun rememberCardSize(): Dp {
    val width = getScreenMetrics().widthDp
    val size = ((width - (16.dp * 7)) / 5)
    return size
}

@Composable
fun PlayingCard(
    cardResource: CardResource,
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
                Column(Modifier.fillMaxSize()) {
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
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                Image(
                    painter = painterResource(Res.drawable.back_blue),
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
            CardState.SELECTED -> Color.Yellow
            CardState.SUCCESS -> Color.Green
            CardState.ERROR -> Color.Red
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
