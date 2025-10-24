package com.defey.solitairewell.di

import com.defey.solitairewell.logic.GameSetupFactory
import com.defey.solitairewell.logic.GameSetupFactoryImpl
import org.koin.dsl.module

val wellDomainModule
    get() = module {
        factory<GameSetupFactory> { GameSetupFactoryImpl() }
    }