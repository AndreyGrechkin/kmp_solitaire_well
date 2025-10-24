package com.defey.solitairewell

import com.defey.solitairewell.factories.CardResourcesFactoryImpl
import com.defey.solitairewell.factories.CardResourcesFactory
import org.koin.dsl.module


val uiKitModule
    get() = module {
        single<CardResourcesFactory> { CardResourcesFactoryImpl() }
    }