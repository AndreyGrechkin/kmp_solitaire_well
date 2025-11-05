package com.defey.solitairewell.managers.billing

import com.defey.solitairewell.models.ProductArticle
import com.defey.solitairewell.models.StoreProduct

interface ProductsRepository {

    suspend fun get(productsIds: List<String>): List<StoreProduct>
    suspend fun checkPurchasedProduct(product: ProductArticle): Boolean
}