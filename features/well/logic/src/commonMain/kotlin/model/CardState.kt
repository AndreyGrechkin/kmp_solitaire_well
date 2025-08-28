package model

enum class CardState {
    DEFAULT,    // Нет выделения
    SELECTED,   // Зеленое выделение (карта выбрана)
    SUCCESS,
    HINTED,
    ERROR       // Красное выделение (ошибка размещения)
}