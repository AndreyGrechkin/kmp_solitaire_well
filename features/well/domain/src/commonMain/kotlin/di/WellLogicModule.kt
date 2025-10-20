package di

import logic.GameSetupFactory
import logic.GameSetupFactoryImpl
import org.koin.dsl.module

val wellLogicModule
    get() = module {
        factory<GameSetupFactory> { GameSetupFactoryImpl() }
    }