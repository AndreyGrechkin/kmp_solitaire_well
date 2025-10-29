package com.defey.solitairewell.logic

import kotlinx.cinterop.ExperimentalForeignApi
import platform.darwin.DISPATCH_SOURCE_TYPE_TIMER
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_time
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.dispatch_resume
import platform.darwin.dispatch_source_cancel
import platform.darwin.dispatch_source_create
import platform.darwin.dispatch_source_set_event_handler
import platform.darwin.dispatch_source_set_timer
import platform.darwin.dispatch_source_t

@Suppress(names = ["EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"])
actual object TimerFactory {
    actual fun create(): CommonTimer = IosTimer()
}

private class IosTimer : CommonTimer {
    private var timer: dispatch_source_t? = null
    private var remainingTime: Long = 0
    private var intervalMillis: Long = 0
    private var onTick: ((Long) -> Unit)? = null
    private var onFinish: (() -> Unit)? = null

    @OptIn(ExperimentalForeignApi::class)
    override fun start(
        durationMillis: Long,
        intervalMillis: Long,
        onTick: ((Long) -> Unit)?,
        onFinish: (() -> Unit)?,
    ) {
        stop()
        remainingTime = durationMillis
        this.intervalMillis = intervalMillis
        this.onTick = onTick
        this.onFinish = onFinish

        val queue = dispatch_get_main_queue()
        val timer = dispatch_source_create(DISPATCH_SOURCE_TYPE_TIMER, 0u, 0u, queue)!!
        this.timer = timer

        dispatch_source_set_timer(
            timer,
            dispatch_time(DISPATCH_TIME_NOW, 0),
            intervalMillis.toULong() * 1_000_000u,
            100000000u
        )

        dispatch_source_set_event_handler(timer) {
            remainingTime -= intervalMillis
            if (remainingTime <= 0L) {
                onFinish?.invoke()
                stop()
            } else {
                onTick?.invoke(remainingTime)
            }
        }
        dispatch_resume(timer)
    }

    override fun stop() {
        timer?.let {
            dispatch_source_cancel(it)
            timer = null
        }
    }

    override fun dispose() {
        stop()
    }
}