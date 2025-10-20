import factories.CardResourcesFactoryImpl
import factories.CardResourcesFactory
import org.koin.dsl.module


val uiKitModule
    get() = module {
        single<CardResourcesFactory> { CardResourcesFactoryImpl() }
    }