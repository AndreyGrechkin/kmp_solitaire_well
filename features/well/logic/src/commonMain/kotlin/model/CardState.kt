package model

enum class CardState {
    DEFAULT,    // Нет выделения
    SELECTED,   // Зеленое выделение (карта выбрана)
    ERROR       // Красное выделение (ошибка размещения)
}