package model

enum class SlotType {
    FOUNDATION,  // 4 слота для сборки
    STOCK,  // Колода
    STOCK_PLAY, // Колода игры 5 слота
    WASTE,       // Склад
    INNER_WELL, // Внутрений колодец 4 слота
    EXTERNAL_WELL // Внешний колодец 4 слота
}