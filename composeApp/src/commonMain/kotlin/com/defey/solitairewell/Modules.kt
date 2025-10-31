package com.defey.solitairewell

import com.defey.solitairewell.di.settingsModule
import com.defey.solitairewell.di.wellModule
import com.defey.solitairewell.di.navigationModule
import languageModule
import monetizationModule

private val coreModules
    get() = listOf(
        commonModule,
        navigationModule,
        uiKitModule,
        languageModule,
        commonAnalyticsModule,
        updateModule,
        monetizationModule
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