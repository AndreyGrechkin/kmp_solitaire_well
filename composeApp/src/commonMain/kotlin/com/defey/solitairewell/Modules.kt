package com.defey.solitairewell

import commonModule
import databaseModule
import di.navigationModule
import storageModule
import wellModule

private val coreModules
    get() = listOf(
        commonModule,
        navigationModule
    )

private val dataModule
    get() = listOf(
        databaseModule,
        storageModule
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