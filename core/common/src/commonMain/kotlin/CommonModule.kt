import logic.CardResourcesFactory
import logic.CardResourcesFactoryImpl
import logic.CommonTimer
import logic.TimerFactory
import org.koin.dsl.module
import repository.WellRepository
import repository.WellRepositoryImpl
import repository.StorageRepository
import repository.StorageRepositoryImpl

val commonModule
    get() = module {
        single<CardResourcesFactory> { CardResourcesFactoryImpl() }
        single<StorageRepository>{ StorageRepositoryImpl(get()) }
        factory <CommonTimer> { TimerFactory.create() }
        factory<WellRepository> { WellRepositoryImpl(get(), get()) }
    }