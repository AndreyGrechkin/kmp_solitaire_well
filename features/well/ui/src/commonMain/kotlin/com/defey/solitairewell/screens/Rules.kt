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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import theme.AppTypography

@Composable
fun RulesContent(
    onConfirm: () -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(24.dp)) {
        Text(
            text = "Правила пасьянса \"Колодец\"",
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            style = AppTypography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Цель игры",
            style = AppTypography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Собрать 4 фундамента - в каждый нужно сложить все карты одной масти (по 2 экземпляра от Короля до Туза).",
            style = AppTypography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Как играть",
            style = AppTypography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "1. Сборка фундаментов",
            style = AppTypography.titleSmall
        )
        Text(
            text = "- На пустой фундамент можно положить Короля).",
            style = AppTypography.bodyMedium
        )
        Text(
            text = "- Далее кладите карты той же масти по убыванию: Дама, Валет, 10... 2, Туз).",
            style = AppTypography.bodyMedium
        )
        Text(
            text = "- После Туза снова кладите Короля, затем Даму, Валета... и так пока не соберете все 26 карт масти).",
            style = AppTypography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "2. Склад",
            style = AppTypography.titleSmall
            )
        Text(
            text = "- На склад можно класть карты одной масти по возрастанию, начиная с Туза).",
            style = AppTypography.bodyMedium
        )
        Text(
            text = "- На пустой склад можно положить только Туза",
            style = AppTypography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "3. Перемещение между колодцами",
            style = AppTypography.titleSmall
        )
        Text(
            text = "- Во внешние колодцы можно класть карты той же масти по возрастанию",
            style = AppTypography.bodyMedium
        )
        Text(
            text = "- На пустой внешний колодец можно положить карту только из внутреннего колодца который рядом.",
            style = AppTypography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "- 4. Работа с запасом",
            style = AppTypography.titleSmall
        )
        Text(
            text = "- Нажимайте на запас, чтобы выложить карты (сначала выкладывается по 5 карт)",
            style = AppTypography.bodyMedium
        )
        Text(
            text = "- Когда запас полностью разложен, он собирается обратно",
            style = AppTypography.bodyMedium
        )
        Text(
            text = "- При следующем раскладе выкладывается на 1 карту меньше (4, потом 3, и т.д.)",
            style = AppTypography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Конец игры",
            style = AppTypography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Вы выиграли: если собрали все 4 фундамента (по 26 карт в каждом)",
            style = AppTypography.bodyMedium
            )
        Text(
            text = "Игра проиграна: если нет возможных ходов, но карты остались в колодцах",
            style = AppTypography.bodyMedium
        )


        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA5D6A7), // светлый зелёный
                    contentColor = Color(0xFF2E7D32)
                )
            ) {
                Text(text = "Понятно")
            }
        }
    }
}