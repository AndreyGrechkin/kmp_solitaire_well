package com.defey.solitairewell.managers.analytics

/**
 * ✅ Expect/actual для платформенной аналитики
 */
interface PlatformAnalytics {
    fun logEvent(event: String, params: Map<String, Any> = emptyMap())
}