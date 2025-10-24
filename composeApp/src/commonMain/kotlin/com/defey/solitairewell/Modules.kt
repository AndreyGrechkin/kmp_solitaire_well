package com.defey.solitairewell

import commonModule
import di.navigationModule
import com.defey.solitairewell.di.settingsModule
import com.defey.solitairewell.di.wellModule
import languageModule

private val coreModules
    get() = listOf(
        commonModule,
        navigationModule,
        uiKitModule,
        languageModule
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