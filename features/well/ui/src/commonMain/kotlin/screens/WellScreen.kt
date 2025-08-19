package screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import getScreenMetrics
import logic.CardResourcesFactory
import model.CardStack
import model.CardState
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
                onClick = { slotAddress ->
                    viewModel.onEvent(WellContract.WellEvent.ClickCard(address = slotAddress))
                }
            )
            MiddleRow(
                stockDeck = state.stock,
                cardFactory = cardFactory,
                onClick = { slotAddress ->
                    viewModel.onEvent(WellContract.WellEvent.ClickCard(address = slotAddress))
                }
            )
            WellSlotBox(
                stackWells = state.stackWells,
                cardFactory = cardFactory,
                onClick = { slotAddress ->
                    viewModel.onEvent(WellContract.WellEvent.ClickCard(address = slotAddress))
                }
            )
        }
    }
}

@Composable
fun TopRow(
    modifier: Modifier,
    onClick: (SlotAddress) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        repeat(5) { index ->
            EmptyCardSlot(
                address = SlotAddress(SlotType.STOCK_PLAY, index),
                onClick = onClick
            )
        }
    }
}

@Composable
fun MiddleRow(
    stockDeck: CardStack,
    cardFactory: CardResourcesFactory,
    onClick: (SlotAddress) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Колода (слева)
        CreateCardSlot(
            stackList = listOf(stockDeck),
            address = SlotAddress(SlotType.STOCK),
            cardFactory = cardFactory,
            onClick = onClick
        )

        Spacer(modifier = Modifier.weight(2f))

        // Склад (справа)
        EmptyCardSlot(
            address = SlotAddress(SlotType.WASTE),
            onClick = onClick
        )
    }
}

@Composable
fun WellSlotBox(
    stackWells: List<CardStack>,
    cardFactory: CardResourcesFactory,
    onClick: (SlotAddress) -> Unit,
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
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = SlotAddress(SlotType.INNER_WELL, TOP_INDEX),
                cardFactory = cardFactory,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = SlotAddress(SlotType.INNER_WELL, BOTTOM_INDEX),
                cardFactory = cardFactory,
                onClick = onClick
            )

            CreateCardSlot(
                stackList = stackWells,
                address = SlotAddress(SlotType.EXTERNAL_WELL, BOTTOM_INDEX),
                cardFactory = cardFactory,
                onClick = onClick
            )

        }
        Column(
            modifier = Modifier
                .padding(top = calculateHalfCardHeight()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                EmptyCardSlot(
                    address = SlotAddress(SlotType.FOUNDATION, LEFT_INDEX),
                    onClick = onClick
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                )
                EmptyCardSlot(
                    address = SlotAddress(SlotType.FOUNDATION, TOP_INDEX),
                    onClick = onClick
                )
            }
            Row() {
                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.EXTERNAL_WELL, LEFT_INDEX),
                    cardFactory = cardFactory,
                    onClick = onClick
                )

                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.INNER_WELL, LEFT_INDEX),
                    cardFactory = cardFactory,
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
                    onClick = onClick
                )

                CreateCardSlot(
                    stackList = stackWells,
                    address = SlotAddress(SlotType.EXTERNAL_WELL, RIGHT_INDEX),
                    cardFactory = cardFactory,
                    onClick = onClick
                )

            }
            Row() {
                EmptyCardSlot(
                    address = SlotAddress(SlotType.FOUNDATION, BOTTOM_INDEX),
                    onClick = onClick
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                )
                EmptyCardSlot(
                    address = SlotAddress(SlotType.FOUNDATION, RIGHT_INDEX),
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
    onClick: (SlotAddress) -> Unit,
) {
    val stack = stackList.find { it.address == address }
    if (stack?.cards.isNullOrEmpty()) {
        EmptyCardSlot(
            address = address,
            onClick = onClick
        )
    } else {
        val topCard = stack.topCard ?: return
        PlayingCard(
            cardResource = cardFactory.createCardResources(topCard),
            isFaceUp = topCard.isFaceUp,
            address = address,
            state = CardState.DEFAULT,
            onClick = onClick
        )
    }
}

@Composable
fun EmptyCardSlot(
    modifier: Modifier = Modifier,
    address: SlotAddress,
    onClick: (SlotAddress) -> Unit = {},
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .width(rememberCardSize())
            .aspectRatio(0.7f)
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                onClick = { onClick(address) }
            )
            .background(
                color = CardColors.defaultEmptyCardSlot,
            )
    )
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
    address: SlotAddress,
    state: CardState = CardState.DEFAULT,
    onClick: (SlotAddress) -> Unit = {},
) {
    // Анимируем цвет обводки
    val borderColor by animateColorAsState(
        targetValue = when (state) {
            CardState.SELECTED -> Color.Green
            CardState.ERROR -> Color.Red
            CardState.DEFAULT -> Color.Transparent
        },
        animationSpec = when (state) {
            CardState.ERROR -> tween(durationMillis = 500, delayMillis = 1000)
            else -> tween(durationMillis = 200)
        }
    )
    Box(
        modifier = modifier
            .padding(8.dp)
            .width(rememberCardSize())
            .aspectRatio(0.7f)
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                onClick = { onClick(address) }
            )
            .background(
                color = CardColors.cardFront,
            )
            .border(
                width = if (state != CardState.DEFAULT) 2.dp else 0.dp,
                color = borderColor,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        if (isFaceUp) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(text = cardResource.rank, color = CardColors.black, fontSize = 32.sp)
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
