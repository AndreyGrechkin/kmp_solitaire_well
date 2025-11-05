package com.defey.solitairewell

import com.defey.solitairewell.di.settingsModule
import com.defey.solitairewell.di.wellModule
import com.defey.solitairewell.di.navigationModule
import com.defey.solitairewell.di.storeModule

private val coreModules
    get() = listOf(
        commonModule,
        navigationModule,
        uiKitModule,
        languageModule,
        commonAnalyticsModule,
        updateModule,
        adsModule
    )

private val dataModule
    get() = listOf(
        databaseModule,
        storageModule,
        dataRepositoryModule,
        storeModule
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