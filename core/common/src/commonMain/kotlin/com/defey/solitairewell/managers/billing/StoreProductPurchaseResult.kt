package com.defey.solitairewell.managers.billing

data class StoreProductPurchaseResult(
    val invoiceId: String,
    val orderId: String?,
    val productId: String,
    val productType: StoreProductType,
    val purchaseId: String,
    val purchaseType: StorePurchaseType,
    val quantity: Int,
    val sandbox: Boolean
)