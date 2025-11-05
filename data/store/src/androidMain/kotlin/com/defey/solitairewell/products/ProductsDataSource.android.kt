package com.defey.solitairewell.products

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.rustore.sdk.core.util.toSuspendResult
import ru.rustore.sdk.pay.RuStorePayClient
import ru.rustore.sdk.pay.model.Product
import ru.rustore.sdk.pay.model.ProductId

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ProductsDataSource(
    private val payClient: RuStorePayClient,
) {

    suspend fun get(productsIds: List<String>): List<Product> =
        withContext(Dispatchers.IO) {
            payClient
                .getProductInteractor()
                .getProducts(productsId = productsIds.map(::ProductId))
                .toSuspendResult()
                .getOrThrow()
        }
}