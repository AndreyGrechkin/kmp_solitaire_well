package com.defey.solitairewell.auth

import com.defey.solitairewell.managers.billing.AuthorizationRepository
import com.defey.solitairewell.managers.billing.PurchaseAvailability
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.rustore.sdk.core.util.toSuspendResult
import ru.rustore.sdk.pay.RuStorePayClient
import ru.rustore.sdk.pay.model.PurchaseAvailabilityResult

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class AuthorizationRepositoryImpl(
    private val payClient: RuStorePayClient,
) : AuthorizationRepository {

    actual override suspend fun getResult(): PurchaseAvailability =
        withContext(Dispatchers.IO) {
            val client = payClient
                .getPurchaseInteractor()
                .getPurchaseAvailability()
                .toSuspendResult()
                .getOrThrow()
            client.toPurchaseAvailability()
        }

    private fun PurchaseAvailabilityResult.toPurchaseAvailability(): PurchaseAvailability =
        when (this) {
            PurchaseAvailabilityResult.Available -> PurchaseAvailability.Available
            is PurchaseAvailabilityResult.Unavailable -> PurchaseAvailability.Unavailable(cause)
        }
}