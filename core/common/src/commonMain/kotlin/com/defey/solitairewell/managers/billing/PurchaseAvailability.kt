package com.defey.solitairewell.managers.billing

sealed class PurchaseAvailability {
    data object Available : PurchaseAvailability()
    data class Unavailable(val cause: Throwable) : PurchaseAvailability()
}