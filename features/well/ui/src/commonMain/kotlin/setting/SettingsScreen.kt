package setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.defey.solitairewell.resources.*
import com.defey.solitairewell.resources.Res
import debugLog
import dialog.CustomDialog
import dialog.rememberDialogController
import factories.CardResourcesFactory
import models.Card
import factories.CardResource
import models.Deck
import models.Rank
import models.Suit
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import rememberCardSize
import theme.CardColors

@Composable
fun SettingsScreen() {
//    val vm: SettingsViewModel = koinViewModel(parameters = { parametersOf(userData) })
    val vm: SettingsViewModel = koinViewModel()
    val cardFactory = koinInject<CardResourcesFactory>()
    val dialogController = rememberDialogController()

    val state by vm.state.collectAsState()
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(CardColors.defaultBackground)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(16.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
            ) {
Box() {
    Image(
        painter = painterResource(Res.drawable.back_move),
        contentDescription = "back",
        modifier = Modifier
            .size(48.dp)
            .clickable(
                onClick = {
                    vm.onEvent(SettingsContract.SettingsEvent.GoBack)
                }
            ),
    )

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center),

        textAlign = TextAlign.Center,
        text = "Настройки",
        color = CardColors.black,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    )
}


                Text(
                    text = "Колода",
                    modifier = Modifier,
                    color = CardColors.black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clickable(
                            onClick = {
                                // Показываем диалог с кастомным содержимым
                                dialogController.showDialog {
                                    FaceCard(
                                        cardFactory,
                                        state.deck,
                                        onConfirm = {
                                            // Логика подтверждения специфичная для Well модуля
                                            debugLog("onConfirm")
                                            dialogController.hideDialog()
                                            vm.onEvent(SettingsContract.SettingsEvent.SaveDeck(it))
                                        },
                                        onDismiss = {
                                            debugLog("onDismiss")
                                            dialogController.hideDialog()
                                        }
                                    )
                                }
                            }
                        ),
                ) {
                    CardFaceRow(cardFactory, state.deck)

                }


                Text(
                    text = "Рубашка",
                    modifier = Modifier,
                    color = CardColors.black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                CardBackSlot(
                    backCard = cardFactory.createBackCard(state.backCardIndex),
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clickable(
                            onClick = {
                                dialogController.showDialog {
                                    BackCard(
                                        cardFactory,
                                        state.backCardIndex,
                                        onConfirm = {
                                            // Логика подтверждения специфичная для Well модуля
                                            debugLog("onConfirm")
                                            dialogController.hideDialog()
                                            vm.onEvent(
                                                SettingsContract.SettingsEvent.SaveBackCard(
                                                    it
                                                )
                                            )
                                        },
                                        onDismiss = {
                                            debugLog("onDismiss")
                                            dialogController.hideDialog()
                                        }
                                    )
                                }
                            }
                        )
                )


                Text(
                    text = "Фон",
                    modifier = Modifier,
                    color = CardColors.black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )


                Text(
                    text = "Отключение рекламы",
                    modifier = Modifier,
                    color = CardColors.black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }
        CustomDialog(controller = dialogController,
            modifier = Modifier.padding(48.dp)
        )
    }
}

@Composable
fun CardFaceRow(
    cardFactory: CardResourcesFactory,
    deck: Deck,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = horizontalArrangement
    ) {
        CardRankSlot(
            cardResource = cardFactory.createCardResources(Card(
                suit = Suit.DIAMONDS,
                rank = Rank.TEN
            ), deck),
            isFaceUp = true
        )
        CardRankSlot(
            cardResource = cardFactory.createCardResources(Card(
                suit = Suit.CLUBS,
                rank = Rank.JACK
            ), deck),
            isFaceUp = true
        )
        CardRankSlot(
            cardResource = cardFactory.createCardResources(Card(
                suit = Suit.HEARTS,
                rank = Rank.QUEEN
            ), deck),
            isFaceUp = true
        )
        CardRankSlot(
            cardResource = cardFactory.createCardResources(Card(
                suit = Suit.SPADES,
                rank = Rank.KING
            ), deck),
            isFaceUp = true
        )
    }
}

    @Composable
    fun CardRankSlot(
        cardResource: CardResource,
        isFaceUp: Boolean,
        modifier: Modifier = Modifier,
    ) {
            Box(
                modifier = modifier
                    .width(rememberCardSize())
                    .aspectRatio(0.7f)
                    .padding(end = 2.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = CardColors.cardFront)
                    .border(
                        width =  1.dp ,
                        color = CardColors.black,
                        shape = MaterialTheme.shapes.medium
                        )
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
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                } else {
                    Image(
                        painter = painterResource(Res.drawable.BC_1),
                        contentDescription = "Card back",
                        modifier = Modifier.fillMaxSize().padding(4.dp),
                    )
                }
            }
        }

@Composable
fun CardBackSlot(
    backCard: DrawableResource,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(rememberCardSize())
            .aspectRatio(0.7f)
            .padding(end = 2.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(color = CardColors.cardFront)
            .border(
                width =  1.dp ,
                color = CardColors.black,
                shape = MaterialTheme.shapes.medium
            )
    ) {
            Image(
                painter = painterResource(backCard),
                contentDescription = "Card back",
                modifier = Modifier.fillMaxSize().padding(4.dp),
            )
        }

}

@Composable
fun FaceCard(
    cardFactory: CardResourcesFactory,
    selectedDeck: Deck? = null,
    onConfirm: (Deck) -> Unit,
    onDismiss: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Icon(
            painter = painterResource(Res.drawable.ic_cancel_24),
            contentDescription = "Закрыть",
            tint = CardColors.heart,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.End)
                .clickable(
                    onClick = onDismiss
                ),

            )
        Text(
            text = "Выберите колоду",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(Deck.entries.toTypedArray()) { deck ->
                val isSelected = deck == selectedDeck
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onConfirm(deck) }
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth().align(Alignment.Center),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = if (isSelected) 4.dp else 4.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) {
                                Color.Green
                            } else {
                                CardColors.cardFront
                            }
                        )
                    ) {
                        Box(
                            modifier = Modifier.padding(8.dp),
                           contentAlignment = Alignment.Center

                        ) {
                            CardFaceRow(
                                cardFactory = cardFactory,
                                deck = deck,
                                horizontalArrangement = Arrangement.SpaceBetween
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BackCard(
    cardFactory: CardResourcesFactory,
    selectedBack: Int? = null,
    modifier: Modifier = Modifier,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Icon(
            painter = painterResource(Res.drawable.ic_cancel_24),
            contentDescription = "Закрыть",
            tint = CardColors.heart,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.End)
                .clickable(
                    onClick = onDismiss
                ),

            )
        Text(
            text = "Выберите рубашку",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(cardFactory.backCards) { index, item ->
                val isSelected = index == selectedBack
                Box(
                    modifier = modifier
                        .width(rememberCardSize())
                        .aspectRatio(0.7f)
                        .padding(end = 2.dp)
                        .clickable { onConfirm(index) }
                        .clip(MaterialTheme.shapes.medium)
                        .background(color = CardColors.cardFront)
                        .border(
                            width =  1.dp ,
                            color = CardColors.black,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth().align(Alignment.Center),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = if (isSelected) 4.dp else 4.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) {
                                Color.Green
                            } else {
                                CardColors.cardFront
                            }
                        )
                    ) {
                        Box(
                            modifier = Modifier.padding(4.dp),
                            contentAlignment = Alignment.Center

                        ) {
                            Image(
                                painter = painterResource(item),
                                contentDescription = "Card back",
                                modifier = Modifier.fillMaxSize().padding(0.dp),
                            )
                        }
                    }

                    }
                }
            }
        }
}

