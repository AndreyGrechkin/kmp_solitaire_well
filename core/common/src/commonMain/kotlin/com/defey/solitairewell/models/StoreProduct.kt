package com.defey.solitairewell.models

data class StoreProduct(
    val productId: String,
    val title: String,
    val description: String?,
    val purchaseStatus: ProductPurchaseStatus,
    val price: Int,
)
