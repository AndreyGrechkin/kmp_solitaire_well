package com.defey.solitairewell.managers.ads

object AdEventBus {
    private val listeners = mutableSetOf<() -> Unit>()

    fun showAd() {
        listeners.forEach { it() }
    }

    fun setOnShowAdListener(listener: () -> Unit) {
        listeners.clear()
        listeners.add(listener)
    }

    fun clear() {
        listeners.clear()
    }
}