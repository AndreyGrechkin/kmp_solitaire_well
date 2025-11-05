package com.defey.solitairewell.products

import com.defey.solitairewell.managers.billing.ProductsRepository
import com.defey.solitairewell.models.ProductArticle
import com.defey.solitairewell.models.StoreProduct

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ProductsRepositoryImpl: ProductsRepository {
    actual override suspend fun get(productsIds: List<String>): List<StoreProduct> {
        TODO("Not yet implemented")
    }

    actual override suspend fun checkPurchasedProduct(product: ProductArticle): Boolean {
        return false
    }
}