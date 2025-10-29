package com.defey.solitairewell.logic

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object TimerFactory {
    fun create(): CommonTimer
}