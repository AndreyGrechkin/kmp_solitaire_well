package com.defey.solitairewell

import com.defey.solitairewell.di.settingsModule
import com.defey.solitairewell.di.wellModule
import commonAnalyticsModule
import commonModule
import di.navigationModule
import languageModule
import updateModule

private val coreModules
    get() = listOf(
        commonModule,
        navigationModule,
        uiKitModule,
        languageModule,
        commonAnalyticsModule,
        updateModule
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