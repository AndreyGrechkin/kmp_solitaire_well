package com.defey.solitairewell.di

import com.defey.solitairewell.auth.AuthorizationRepositoryImpl
import com.defey.solitairewell.managers.billing.AuthorizationRepository
import com.defey.solitairewell.managers.billing.ProductsRepository
import com.defey.solitairewell.managers.billing.PurchasesRepository
import com.defey.solitairewell.products.ProductsDataSource
import com.defey.solitairewell.products.ProductsRepositoryImpl
import com.defey.solitairewell.purchases.PurchaseStatusMapper
import com.defey.solitairewell.products.StoreProductConverter
import com.defey.solitairewell.purchases.DeviceIdDataSource
import com.defey.solitairewell.purchases.PurchasesDataSource
import com.defey.solitairewell.purchases.PurchasesRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.rustore.sdk.pay.RuStorePayClient

actual val storeModule: Module
    get() = module {
        single<AuthorizationRepository> { AuthorizationRepositoryImpl(get()) }
        single<RuStorePayClient> { RuStorePayClient.instance }
        factory<PurchaseStatusMapper> { PurchaseStatusMapper() }
        single<StoreProductConverter> { StoreProductConverter(get()) }
        single<ProductsDataSource> { ProductsDataSource(get()) }
        single<PurchasesDataSource> { PurchasesDataSource(get()) }
        single<ProductsRepository> { ProductsRepositoryImpl(get(), get(), get()) }
        single<DeviceIdDataSource> { DeviceIdDataSource(get()) }
        single<PurchasesRepository> { PurchasesRepositoryImpl(get(), get(), get()) }
    }