package com.defey.solitairewell.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.defey.solitairewell.WellViewModel

val wellModule
    get() = module {
        includes(wellDomainModule)
        viewModel { WellViewModel(get(), get(), get(), get(), get()) }
    }