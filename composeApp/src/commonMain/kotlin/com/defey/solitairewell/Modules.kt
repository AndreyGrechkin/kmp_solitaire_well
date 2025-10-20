package com.defey.solitairewell

import commonModule
import dataRepositoryModule
import databaseModule
import di.navigationModule
import storageModule
import uiKitModule
import wellModule

private val coreModules
    get() = listOf(
        commonModule,
        navigationModule,
        uiKitModule
    )

private val dataModule
    get() = listOf(
        databaseModule,
        storageModule,
        dataRepositoryModule
    )

private val featureModules
    get() = listOf(
        wellModule
    )

val appModules
    get() = listOf(
        coreModules,
        dataModule,
        featureModules,
    ).flatten()