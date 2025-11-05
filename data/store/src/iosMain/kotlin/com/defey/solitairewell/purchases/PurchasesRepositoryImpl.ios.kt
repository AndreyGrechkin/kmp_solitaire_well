package com.defey.solitairewell.purchases

import com.defey.solitairewell.managers.billing.PurchasesRepository
import com.defey.solitairewell.managers.billing.StorePreferredPurchaseType
import com.defey.solitairewell.managers.billing.StoreProductPurchaseResult
import com.defey.solitairewell.models.ProductArticle
import com.defey.solitairewell.models.StorePurchase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PurchasesRepositoryImpl :
    PurchasesRepository {
    actual override suspend fun purchase(
        productId: ProductArticle,
        purchaseType: StorePreferredPurchaseType
    ): StoreProductPurchaseResult {
        TODO("Not yet implemented")
    }

    actual override suspend fun purchaseWithTwoStep(productId: ProductArticle): StoreProductPurchaseResult {
        TODO("Not yet implemented")
    }

    actual override suspend fun confirmPurchase(purchaseId: String) {
    }

    actual override suspend fun cancelPurchase(purchaseId: String) {
    }

    actual override suspend fun getPurchases(): List<StorePurchase> {
        TODO("Not yet implemented")
    }
}