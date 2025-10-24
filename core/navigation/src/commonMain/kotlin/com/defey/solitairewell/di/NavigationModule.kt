package di

import base.NavigationManager
import org.koin.dsl.module

val navigationModule = module {
    single<NavigationManager> { NavigationManager() }
}
