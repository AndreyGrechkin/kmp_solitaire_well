package com.defey.solitairewell.managers.billing

interface AuthorizationRepository {
    suspend fun getResult(): PurchaseAvailability
}