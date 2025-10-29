package com.defey.solitairewell.managers

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
interface Localization {
    fun applyLanguage(iso: String)
    fun getSystemLanguageCode(): String
}