package com.defey.solitairewell.managers.analytics

/**
 * ✅ Сбор аналитики и метрик
 * Отвечает за:
 * - Отслеживание игровых событий (старт игры, завершение)
 * - Метрики монетизации (показы рекламы, покупки)
 * - Пользовательскую аналитику (retention, session length)
 */
class AnalyticsManager(
    private val platformAnalytics: PlatformAnalytics
) {

    fun logGameStarted() {
        logEvent("game_started")
    }

    fun logGameFinished(won: Boolean, duration: Long, moves: Int) {
        logEvent(
            "game_finished", mapOf(
                "won" to won,
                "duration_sec" to duration,
                "moves_count" to moves
            )
        )
    }

    fun logGameAbandoned() {
        logEvent("game_abandoned")
    }

    fun logAppLaunch() {
        logEvent("app_launch")
    }

    fun logScreenView(screenName: String) {
        logEvent("screen_view", mapOf("screen" to screenName))
    }

    fun logCustomEvent(event: String, params: Map<String, Any> = emptyMap()) {
        logEvent(event, params)
    }

    fun logEvent(event: String, params: Map<String, Any> = emptyMap()) {
        platformAnalytics.logEvent(event, params)
    }
}