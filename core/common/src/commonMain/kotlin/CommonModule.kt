import logic.CardResourcesFactory
import logic.CardResourcesFactoryImpl
import logic.CommonTimer
import logic.TimerFactory
import org.koin.dsl.module

val commonModule
    get() = module {
        single<CardResourcesFactory> { CardResourcesFactoryImpl() }
        factory <CommonTimer> { TimerFactory.create() }
    }