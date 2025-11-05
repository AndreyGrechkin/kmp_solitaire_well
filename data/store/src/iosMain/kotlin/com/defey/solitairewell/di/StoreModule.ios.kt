package com.defey.solitairewell.di

import com.defey.solitairewell.auth.AuthorizationRepositoryImpl
import com.defey.solitairewell.managers.billing.AuthorizationRepository
import org.koin.core.module.Module
import org.koin.dsl.module

actual val storeModule: Module
    get() = module {
        single<AuthorizationRepository> { AuthorizationRepositoryImpl() }
    }