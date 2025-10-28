package com.defey.solitairewell.managers.analytics

class DesktopAnalytics : PlatformAnalytics {

    override fun logEvent(event: String, params: Map<String, Any>) {
        println("Desktop Analytics Event: $event - $params")
    }
}