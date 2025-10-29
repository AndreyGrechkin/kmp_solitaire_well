package com.defey.solitairewell.di

import com.defey.solitairewell.base.NavigationManager
import org.koin.dsl.module

val navigationModule = module {
    single<NavigationManager> { NavigationManager() }
}
