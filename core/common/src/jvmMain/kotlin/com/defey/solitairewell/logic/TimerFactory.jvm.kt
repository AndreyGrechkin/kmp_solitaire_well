package com.defey.solitairewell.logic


import java.util.Timer
import java.util.TimerTask

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object TimerFactory {
    actual fun create(): CommonTimer = JvmCommonTimer()
}

private class JvmCommonTimer : CommonTimer {
    private val commonTimer = Timer()
    private var tasks: MutableList<TimerTask> = mutableListOf()

    override fun start(
        durationMillis: Long,
        intervalMillis: Long,
        onTick: ((Long) -> Unit)?,
        onFinish: (() -> Unit)?
    ) {
        stop()

        val totalIntervals = durationMillis / intervalMillis

        // Создаем задачи для тиков
        for (i in 0 until totalIntervals) {
            val task = object : TimerTask() {
                override fun run() {
                    val remaining = durationMillis - (i + 1) * intervalMillis
                    onTick?.invoke(remaining)
                }
            }
            commonTimer.schedule(task, (i + 1) * intervalMillis)
            tasks.add(task)
        }

        // Задача для завершения
        val finishTask = object : TimerTask() {
            override fun run() {
                onFinish?.invoke()
            }
        }
        commonTimer.schedule(finishTask, durationMillis)
        tasks.add(finishTask)
    }

    override fun stop() {
        tasks.forEach { it.cancel() }
        tasks.clear()
    }

    override fun dispose() {
        stop()
        commonTimer.cancel()
    }
}