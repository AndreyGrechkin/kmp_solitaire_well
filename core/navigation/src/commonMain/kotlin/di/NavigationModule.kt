package di

import base.NavigationLogger
import base.NavigationLoggerImpl
import base.Router
import base.RouterImpl
import org.koin.dsl.module

val navigationModule = module {
    single<Router> { RouterImpl(get()) }
    single<NavigationLogger> { NavigationLoggerImpl() }
}
