package com.defey.solitairewell.models

import com.defey.solitairewell.managers.billing.StorePurchaseStatus
import com.defey.solitairewell.managers.billing.StorePurchaseType

interface  StorePurchase {
    val amountLabel: String
    val currency: String
    val description: String
    val developerPayload: String?
    val invoiceId: String
    val orderId: String?
    val price: Int
    val purchaseId: String
    val purchaseTime: Long?
    val purchaseType: StorePurchaseType
    val sandbox: Boolean
    val status: StorePurchaseStatus
}