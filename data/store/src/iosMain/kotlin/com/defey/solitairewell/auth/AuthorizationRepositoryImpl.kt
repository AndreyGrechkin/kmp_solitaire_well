package com.defey.solitairewell.auth

import com.defey.solitairewell.managers.billing.AuthorizationRepository
import com.defey.solitairewell.managers.billing.PurchaseAvailability

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AuthorizationRepositoryImpl : AuthorizationRepository {
    actual override suspend fun getResult(): PurchaseAvailability {
        TODO("Not yet implemented")
    }
}