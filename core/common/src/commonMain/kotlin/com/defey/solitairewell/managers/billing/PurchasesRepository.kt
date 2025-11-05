package com.defey.solitairewell.managers.billing

import com.defey.solitairewell.models.ProductArticle
import com.defey.solitairewell.models.StorePurchase

interface PurchasesRepository {

    suspend fun purchase(
        productId: ProductArticle,
        purchaseType: StorePreferredPurchaseType,
    ): StoreProductPurchaseResult

    suspend fun purchaseWithTwoStep(productId: ProductArticle): StoreProductPurchaseResult

    suspend fun confirmPurchase(purchaseId: String)
    suspend fun cancelPurchase(purchaseId: String)
    suspend fun getPurchases(): List<StorePurchase>
}