package com.defey.solitairewell.products

import com.defey.solitairewell.managers.billing.ProductsRepository
import com.defey.solitairewell.models.ProductArticle
import com.defey.solitairewell.models.StoreProduct

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ProductsRepositoryImpl : ProductsRepository {
    override suspend fun get(productsIds: List<String>): List<StoreProduct>
    override suspend fun checkPurchasedProduct(product: ProductArticle): Boolean
}