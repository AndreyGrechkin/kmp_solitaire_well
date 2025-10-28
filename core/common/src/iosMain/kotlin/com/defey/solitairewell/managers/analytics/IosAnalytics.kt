package com.defey.solitairewell.managers.analytics

class IosAnalytics : PlatformAnalytics {

    override fun logEvent(event: String, params: Map<String, Any>) {
        println("iOS Analytics Event: $event - $params")
    }
}