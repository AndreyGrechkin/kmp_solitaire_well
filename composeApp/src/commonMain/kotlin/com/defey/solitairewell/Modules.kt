package com.defey.solitairewell

import analyticsModule
import commonModule
import di.navigationModule
import com.defey.solitairewell.di.settingsModule
import com.defey.solitairewell.di.wellModule
import commonAnalyticsModule
import languageModule

private val coreModules
    get() = listOf(
        commonModule,
        navigationModule,
        uiKitModule,
        languageModule,
        commonAnalyticsModule
    )

private val dataModule
    get() = listOf(
        databaseModule,
        storageModule,
        dataRepositoryModule
    )

private val featureModules
    get() = listOf(
        wellModule,
        settingsModule
    )

val appModules
    get() = listOf(
        coreModules,
        dataModule,
        featureModules,
    ).flatten()