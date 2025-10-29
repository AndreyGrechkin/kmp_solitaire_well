package com.defey.solitairewell.logic

import android.os.CountDownTimer

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object TimerFactory {
    actual fun create(): CommonTimer = AndroidCommonTimer()
}

private class AndroidCommonTimer : CommonTimer {
    private var countDownTimer: CountDownTimer? = null

    override fun start(
        durationMillis: Long,
        intervalMillis: Long,
        onTick: ((Long) -> Unit)?,
        onFinish: (() -> Unit)?
    ) {
        stop()
        countDownTimer = object : CountDownTimer(durationMillis, intervalMillis) {
            override fun onTick(millisUntilFinished: Long) {
                onTick?.invoke(millisUntilFinished)
            }

            override fun onFinish() {
                onFinish?.invoke()
            }
        }.start()
    }

    override fun stop() {
        countDownTimer?.cancel()
    }

    override fun dispose() {
        stop()
        countDownTimer = null
    }
}