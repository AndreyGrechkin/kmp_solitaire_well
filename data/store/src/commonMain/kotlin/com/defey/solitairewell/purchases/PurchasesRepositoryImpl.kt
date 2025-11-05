package com.defey.solitairewell.purchases

import com.defey.solitairewell.managers.billing.PurchasesRepository
import com.defey.solitairewell.managers.billing.StorePreferredPurchaseType
import com.defey.solitairewell.managers.billing.StoreProductPurchaseResult
import com.defey.solitairewell.models.ProductArticle
import com.defey.solitairewell.models.StorePurchase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class PurchasesRepositoryImpl : PurchasesRepository {
    override suspend fun purchase(
        productId: ProductArticle,
        purchaseType: StorePreferredPurchaseType
    ): StoreProductPurchaseResult

    override suspend fun purchaseWithTwoStep(productId: ProductArticle): StoreProductPurchaseResult
    override suspend fun confirmPurchase(purchaseId: String)
    override suspend fun cancelPurchase(purchaseId: String)
    override suspend fun getPurchases(): List<StorePurchase>
}