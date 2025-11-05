package com.defey.solitairewell.managers.billing

sealed interface StorePurchaseStatus {

    enum class ProductPurchaseStatus : StorePurchaseStatus {
        INVOICE_CREATED,
        EXECUTING,
        PROCESSING,
        CANCELLED,
        REJECTED,
        EXPIRED,
        PAID,
        CONFIRMED,
        REVERSED,
        REFUNDED,
        REFUNDING,
    }

    enum class SubscriptionPurchaseStatus : StorePurchaseStatus {
        INVOICE_CREATED,
        CANCELLED,
        EXPIRED,
        PROCESSING,
        REJECTED,
        ACTIVE,
        PAUSED,
        TERMINATED,
        CLOSED,
    }

    data object UnknownTypeStatus : StorePurchaseStatus
}