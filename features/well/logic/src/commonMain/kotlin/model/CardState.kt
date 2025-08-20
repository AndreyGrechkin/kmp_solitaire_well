package model

enum class CardState {
    DEFAULT,    // Нет выделения
    SELECTED,   // Зеленое выделение (карта выбрана)
    SUCCESS,
    ERROR       // Красное выделение (ошибка размещения)
}