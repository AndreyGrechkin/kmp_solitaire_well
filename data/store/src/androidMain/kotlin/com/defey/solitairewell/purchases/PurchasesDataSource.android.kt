package com.defey.solitairewell.purchases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.rustore.sdk.core.util.toSuspendResult
import ru.rustore.sdk.pay.RuStorePayClient
import ru.rustore.sdk.pay.model.AppUserId
import ru.rustore.sdk.pay.model.PreferredPurchaseType
import ru.rustore.sdk.pay.model.ProductId
import ru.rustore.sdk.pay.model.ProductPurchaseParams
import ru.rustore.sdk.pay.model.ProductPurchaseResult
import ru.rustore.sdk.pay.model.ProductType
import ru.rustore.sdk.pay.model.Purchase
import ru.rustore.sdk.pay.model.PurchaseId
import ru.rustore.sdk.pay.model.PurchaseStatus

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PurchasesDataSource(
    private val payClient: RuStorePayClient
) {

    suspend fun get(productType: ProductType?, purchaseStatus: PurchaseStatus?): List<Purchase> =
        withContext(Dispatchers.IO) {
            payClient
                .getPurchaseInteractor()
                .getPurchases(productType = productType, purchaseStatus = purchaseStatus)
                .toSuspendResult()
                .getOrThrow()
        }

    suspend fun purchase(
        productId: ProductId,
        purchaseType: PreferredPurchaseType,
        appUserId: AppUserId?,
    ): ProductPurchaseResult =
        withContext(Dispatchers.IO) {
            payClient
                .getPurchaseInteractor()
                .purchase(
                    params = ProductPurchaseParams(
                        productId = productId,
                        appUserId = appUserId,
                    ),
                    preferredPurchaseType = purchaseType,
                )
                .toSuspendResult()
                .getOrThrow()
        }

    suspend fun purchaseWithTwoStep(
        productId: ProductId,
        appUserId: AppUserId?
    ): ProductPurchaseResult =
        withContext(Dispatchers.IO) {
            payClient
                .getPurchaseInteractor()
                .purchaseTwoStep(
                    params = ProductPurchaseParams(
                        productId = productId,
                        appUserId = appUserId,
                    ),
                )
                .toSuspendResult()
                .getOrThrow()
        }

    suspend fun confirmPurchase(purchaseId: PurchaseId) {
        withContext(Dispatchers.IO) {
            payClient
                .getPurchaseInteractor()
                .confirmTwoStepPurchase(purchaseId = purchaseId, developerPayload = null)
                .toSuspendResult()
                .getOrThrow()
        }
    }

    suspend fun cancelPurchase(purchaseId: PurchaseId) {
        withContext(Dispatchers.IO) {
            payClient
                .getPurchaseInteractor()
                .cancelTwoStepPurchase(purchaseId = purchaseId)
                .toSuspendResult()
                .getOrThrow()
        }
    }
}