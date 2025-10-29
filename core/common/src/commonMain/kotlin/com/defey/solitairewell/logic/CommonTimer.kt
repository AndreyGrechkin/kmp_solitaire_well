package com.defey.solitairewell.logic

interface CommonTimer {
    fun start(
        durationMillis: Long,
        intervalMillis: Long = 1000,
        onTick: ((Long) -> Unit)? = null,
        onFinish: (() -> Unit)? = null
    )
    fun stop()
    fun dispose()

    companion object Companion {
        fun create(): CommonTimer = TimerFactory.create()
    }
}
