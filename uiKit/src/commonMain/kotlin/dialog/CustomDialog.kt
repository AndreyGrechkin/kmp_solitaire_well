package dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import theme.CardColors

/**
 * Кастомный диалог для использования во всех модулях приложения.
 *
 * @param controller Контроллер управления состоянием диалога
 * @param modifier Модификатор для контейнера содержимого диалога
 * @param backgroundColor Цфет фона содержимого диалога
 * @param overlayColor Цвет оверлея (фона за диалогом)
 * @param dismissOnBackPress Закрывать ли диалог по кнопке назад
 * @param dismissOnClickOutside Закрывать ли диалог при клике вне его
 * @param properties Свойства диалога (зависят от платформы)
 */
@Composable
fun CustomDialog(
    controller: DialogController,
    modifier: Modifier = Modifier,
    backgroundColor: Color = CardColors.dialogColor,
    overlayColor: Color = Color.Black.copy(alpha = 0.5f),
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    properties: DialogProperties = DialogProperties(),
    // Новые параметры для кастомизации
//    dialogModifier: Modifier = Modifier,

//    overlayModifier: Modifier = Modifier.fillMaxSize(),
    contentAlignment: Alignment = Alignment.Center
) {
    // Показываем диалог только если isVisible = true
    if (controller.isVisible) {
        // PlatformDialog - expect функция, реализация зависит от платформы
        PlatformDialog(
            onDismissRequest = {
                // Вызывается при попытке закрыть диалог
                if (dismissOnClickOutside) controller.hideDialog()
            },
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = properties.dismissOnClickOutside,
                usePlatformDefaultWidth = false
            )
        ) {
            // Оверлей - затемненный фон
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        enabled = dismissOnClickOutside,
                        onClick = { controller.hideDialog() }
                    )
                    .background(overlayColor),
                contentAlignment = contentAlignment
            ) {
                // Контейнер содержимого диалога
                Box(
                    modifier = modifier
//                        .then(dialogModifier) // Применяем кастомный модификатор
                        .sizeIn(maxWidth = 400.dp, maxHeight = 600.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(backgroundColor) // Фон содержимого
                        .clickable(enabled = false, onClick = {}) // Блокируем клики на самом контенте
                ) {
                    // Отображаем содержимое из контроллера
                    controller.content?.invoke()
                }
            }
        }
    }
}

/**
 * Expect функция для платформо-специфичной реализации диалога.
 * Каждая платформа (Android, iOS, Desktop) предоставляет свою реализацию.
 */
@Composable
expect fun PlatformDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    content: @Composable () -> Unit
)