package com.defey.solitairewell.auth

import com.defey.solitairewell.managers.billing.AuthorizationRepository
import com.defey.solitairewell.managers.billing.PurchaseAvailability

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class AuthorizationRepositoryImpl: AuthorizationRepository {
    override suspend fun getResult(): PurchaseAvailability
}