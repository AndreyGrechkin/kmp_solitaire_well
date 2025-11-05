package com.defey.solitairewell.purchases

import com.defey.solitairewell.managers.billing.StorePurchaseStatus
import ru.rustore.sdk.pay.model.ProductPurchaseStatus
import ru.rustore.sdk.pay.model.PurchaseStatus
import ru.rustore.sdk.pay.model.SubscriptionPurchaseStatus

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PurchaseStatusMapper {

    fun mapToModel(sdkStatus: PurchaseStatus): StorePurchaseStatus =
        when (sdkStatus) {
            is ProductPurchaseStatus -> mapProductPurchaseStatus(sdkStatus)
            is SubscriptionPurchaseStatus -> mapSubscriptionPurchaseStatus(sdkStatus)
            else -> StorePurchaseStatus.UnknownTypeStatus
        }

    private fun mapProductPurchaseStatus(
        purchaseStatus: ProductPurchaseStatus
    ): StorePurchaseStatus = when (purchaseStatus) {
        ProductPurchaseStatus.INVOICE_CREATED -> StorePurchaseStatus.ProductPurchaseStatus.INVOICE_CREATED
        ProductPurchaseStatus.PROCESSING -> StorePurchaseStatus.ProductPurchaseStatus.PROCESSING
        ProductPurchaseStatus.EXECUTING -> StorePurchaseStatus.ProductPurchaseStatus.EXECUTING
        ProductPurchaseStatus.CANCELLED -> StorePurchaseStatus.ProductPurchaseStatus.CANCELLED
        ProductPurchaseStatus.REJECTED -> StorePurchaseStatus.ProductPurchaseStatus.REJECTED
        ProductPurchaseStatus.EXPIRED -> StorePurchaseStatus.ProductPurchaseStatus.EXPIRED
        ProductPurchaseStatus.PAID -> StorePurchaseStatus.ProductPurchaseStatus.PAID
        ProductPurchaseStatus.CONFIRMED -> StorePurchaseStatus.ProductPurchaseStatus.CONFIRMED
        ProductPurchaseStatus.REVERSED -> StorePurchaseStatus.ProductPurchaseStatus.REVERSED
        ProductPurchaseStatus.REFUNDED -> StorePurchaseStatus.ProductPurchaseStatus.REFUNDED
        ProductPurchaseStatus.REFUNDING -> StorePurchaseStatus.ProductPurchaseStatus.REFUNDING
    }

    private fun mapSubscriptionPurchaseStatus(
        purchaseStatus: SubscriptionPurchaseStatus
    ): StorePurchaseStatus = when (purchaseStatus) {
        SubscriptionPurchaseStatus.INVOICE_CREATED -> StorePurchaseStatus.SubscriptionPurchaseStatus.INVOICE_CREATED
        SubscriptionPurchaseStatus.CANCELLED -> StorePurchaseStatus.SubscriptionPurchaseStatus.CANCELLED
        SubscriptionPurchaseStatus.EXPIRED -> StorePurchaseStatus.SubscriptionPurchaseStatus.EXPIRED
        SubscriptionPurchaseStatus.PROCESSING -> StorePurchaseStatus.SubscriptionPurchaseStatus.PROCESSING
        SubscriptionPurchaseStatus.REJECTED -> StorePurchaseStatus.SubscriptionPurchaseStatus.REJECTED
        SubscriptionPurchaseStatus.ACTIVE -> StorePurchaseStatus.SubscriptionPurchaseStatus.ACTIVE
        SubscriptionPurchaseStatus.PAUSED -> StorePurchaseStatus.SubscriptionPurchaseStatus.PAUSED
        SubscriptionPurchaseStatus.TERMINATED -> StorePurchaseStatus.SubscriptionPurchaseStatus.TERMINATED
        SubscriptionPurchaseStatus.CLOSED -> StorePurchaseStatus.SubscriptionPurchaseStatus.CLOSED
    }
}