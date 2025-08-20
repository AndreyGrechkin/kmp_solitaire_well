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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.defey.solitairewell.data.resources.Res
import com.defey.solitairewell.data.resources.back_blue
import debugLog
import getScreenMetrics
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
import statusBarPadding
import theme.CardColors

@Composable
fun WellScreen() {
    val viewModel: WellViewModel = koinViewModel()
    val cardFactory = koinInject<CardResourcesFactory>()
    val state by viewModel.state.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarPadding()
            .background(CardColors.defaultBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        viewModel.onEvent(WellContract.WellEvent.OnNewGame)
                    }
                ) {
                    Text(text = "Новая игра")
                }
                Button(
                    onClick = {

                    }
                ) {
                    Text(text = "Отменить ход")
                }
            }
            TopRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                stackWells = state.stackWells,
                cardFactory = cardFactory,
                gameState = state.gameState,
                onClick = { state ->
                    viewModel.onEvent(WellContract.WellEvent.ClickCard(state = state))
                }
            )
            MiddleRow(
                stackWells = state.stackWells,
                cardFactory = cardFactory,
                gameState = state.gameState,
                onClick = { state ->
                    viewModel.onEvent(WellContract.WellEvent.ClickCard(state = state))
                }
            )
            WellSlotBox(
                stackWells = state.stackWells,
                cardFactory = cardFactory,
                gameState = state.gameState,
                onClick = { state ->
                    viewModel.onEvent(WellContract.WellEvent.ClickCard(state = state))
                }
            )
        }
    }
}

@Composable
fun TopRow(
    stackWells: List<CardStack>,
    cardFactory: CardResourcesFactory,
    modifier: Modifier,
    gameState: GameState,
    onClick: (GameState) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        repeat(5) { index ->
            CreateCardSlot(
                stackList = stackWells,
                address = SlotAddress(SlotType.STOCK_PLAY, index),
                cardFactory = cardFactory,
                gameState = gameState,
                onClick = onClick
            )
        }
    }
}

@Composable
fun MiddleRow(
    stackWells: List<CardStack>,
    gameState: GameState,
    cardFactory: CardResourcesFactory,
    onClick: (GameState) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Колода (слева)
        CreateCardSlot(
            stackList = stackWells,
            address = SlotAddress(SlotType.STOCK),
            cardFactory = cardFactory,
            gameState = gameState,
            onClick = onClick
        )

        Spacer(modifier = Modifier.weight(2f))

        // Склад (справа)
        CreateCardSlot(
            stackList = stackWells,
            address = SlotAddress(SlotType.WASTE),
            cardFactory = cardFactory,
            gameState = gameState,
            onClick = onClick
        )
    }
}

@Composable
fun WellSlotBox(
    stackWells: List<CardStack>,
    gameState: GameState,
    cardFactory: CardResourcesFactory,
    onClick: (GameState) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            CreateCardSlot(
                stackList = stackWells,
                address = SlotAddress(SlotType.EXTERNAL_WELL, TOP_INDEX),
                cardFactory = cardFactory,
                gameState = gameState,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = SlotAddress(SlotType.INNER_WELL, TOP_INDEX),
                cardFactory = cardFactory,
                gameState = gameState,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = SlotAddress(SlotType.INNER_WELL, BOTTOM_INDEX),
                cardFactory = cardFactory,
                gameState = gameState,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = SlotAddress(SlotType.EXTERNAL_WELL, BOTTOM_INDEX),
                cardFactory = cardFactory,
                gameState = gameState,
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
                    onClick = onClick
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                )
                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.FOUNDATION, TOP_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    onClick = onClick
                )
            }
            Row() {
                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.EXTERNAL_WELL, LEFT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    onClick = onClick
                )

                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.INNER_WELL, LEFT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    onClick = onClick
                )

                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                )
                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.INNER_WELL, RIGHT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    onClick = onClick
                )

                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.EXTERNAL_WELL, RIGHT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    onClick = onClick
                )

            }
            Row() {
                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.FOUNDATION, BOTTOM_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
                    onClick = onClick
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                )
                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.FOUNDATION, RIGHT_INDEX),
                    cardFactory = cardFactory,
                    gameState = gameState,
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
    onClick: (GameState) -> Unit,
) {
    val stack = stackList.find { it.address == address }
    val state = if (gameState.address == address) gameState.state
    else CardState.DEFAULT
    if (stack?.cards.isNullOrEmpty()) {
        EmptyCardSlot(
            state = state,
            onAnimationComplete = {
                onClick(
                    GameState(
                        card = null,
                        address = address,
                        state = state
                    )
                )
                debugLog("finis Anim")
            },
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
            cardResource = cardFactory.createCardResources(topCard),
            isFaceUp = topCard.isFaceUp,
            state = state,
            onAnimationComplete = {
                onClick(
                    GameState(
                        card = topCard,
                        address = address,
                        state = state
                    )
                )
                debugLog("finis Anim")
            },
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
    state: CardState,
    onAnimationComplete: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    AnimatedBorder(
        state = state,
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
    isFaceUp: Boolean,
    modifier: Modifier = Modifier,
    state: CardState = CardState.DEFAULT,
    onAnimationComplete: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    AnimatedBorder(
        state = state,
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
                            modifier = Modifier.padding(start = 8.dp).size(24.dp),
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
    onAnimationComplete: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    val borderColor by animateColorAsState(
        targetValue = when (state) {
            CardState.SELECTED -> Color.Yellow
            CardState.SUCCESS -> Color.Green
            CardState.ERROR -> Color.Red
            CardState.DEFAULT -> Color.Transparent
        },
        animationSpec = when (state) {
            CardState.SELECTED -> tween(durationMillis = 200)
            CardState.DEFAULT -> tween(durationMillis = 200)
            else -> tween(durationMillis = 500)
        },
        finishedListener = {
            if (state == CardState.SUCCESS || state == CardState.ERROR) {
                onAnimationComplete()
            }
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

    Box(
        modifier = Modifier
            .padding(8.dp)
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
