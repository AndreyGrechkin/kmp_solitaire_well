import logic.CardResourcesFactory
import logic.CardResourcesFactoryImpl
import org.koin.dsl.module

val commonModule
    get() = module {
        single<CardResourcesFactory> { CardResourcesFactoryImpl() }
    }