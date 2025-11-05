package com.defey.solitairewell.models

import com.defey.solitairewell.managers.billing.StorePurchaseStatus

sealed class ProductPurchaseStatus {

    data object NotExist : ProductPurchaseStatus()

    data class Exist(
        val purchaseId: String,
        val purchaseStatus: StorePurchaseStatus,
        val developerPayload: String?,
    ) : ProductPurchaseStatus()
}