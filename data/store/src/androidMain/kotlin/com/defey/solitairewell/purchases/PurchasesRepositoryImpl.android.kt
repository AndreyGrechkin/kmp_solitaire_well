package com.defey.solitairewell.purchases

import com.defey.solitairewell.managers.billing.PurchasesRepository
import com.defey.solitairewell.managers.billing.StorePreferredPurchaseType
import com.defey.solitairewell.managers.billing.StoreProductPurchaseResult
import com.defey.solitairewell.managers.billing.StoreProductType
import com.defey.solitairewell.managers.billing.StorePurchaseStatus
import com.defey.solitairewell.managers.billing.StorePurchaseType
import com.defey.solitairewell.models.ProductArticle
import com.defey.solitairewell.models.StorePurchase
import ru.rustore.sdk.pay.model.AppUserId
import ru.rustore.sdk.pay.model.PreferredPurchaseType
import ru.rustore.sdk.pay.model.ProductId
import ru.rustore.sdk.pay.model.ProductPurchaseResult
import ru.rustore.sdk.pay.model.ProductType
import ru.rustore.sdk.pay.model.Purchase
import ru.rustore.sdk.pay.model.PurchaseId
import ru.rustore.sdk.pay.model.PurchaseType

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PurchasesRepositoryImpl(
    private val purchasesDataSource: PurchasesDataSource,
    private val deviceIdDataSource: DeviceIdDataSource,
    private val purchaseStatusMapper: PurchaseStatusMapper,
) :
    PurchasesRepository {
    actual override suspend fun purchase(
        productId: ProductArticle,
        purchaseType: StorePreferredPurchaseType
    ): StoreProductPurchaseResult {
        val productPurchaseResult = purchasesDataSource.purchase(
            ProductId(productId.id),
            purchaseType.toPreferredPurchaseType(),
            AppUserId(deviceIdDataSource.get()),
        )
        return productPurchaseResult.toStoreProductPurchaseResult()
    }

    actual override suspend fun purchaseWithTwoStep(productId: ProductArticle): StoreProductPurchaseResult {
        val productPurchaseResult = purchasesDataSource.purchaseWithTwoStep(
            ProductId(productId.id),
            AppUserId(deviceIdDataSource.get())
        )
        return productPurchaseResult.toStoreProductPurchaseResult()
    }


    actual override suspend fun confirmPurchase(purchaseId: String) {
        purchasesDataSource.confirmPurchase(PurchaseId(purchaseId))
    }

    actual override suspend fun cancelPurchase(purchaseId: String) {
        purchasesDataSource.cancelPurchase(PurchaseId(purchaseId))
    }

    actual override suspend fun getPurchases(): List<StorePurchase> =
        purchasesDataSource.get(productType = null, purchaseStatus = null).map {
            it.toStorePurchase()
        }

    private fun StorePreferredPurchaseType.toPreferredPurchaseType(): PreferredPurchaseType =
        when (this) {
            StorePreferredPurchaseType.ONE_STEP -> PreferredPurchaseType.ONE_STEP
            StorePreferredPurchaseType.TWO_STEP -> PreferredPurchaseType.TWO_STEP
        }

    private fun ProductType.toStoreProductType(): StoreProductType =
        when (this) {
            ProductType.NON_CONSUMABLE_PRODUCT -> StoreProductType.NON_CONSUMABLE_PRODUCT
            ProductType.CONSUMABLE_PRODUCT -> StoreProductType.CONSUMABLE_PRODUCT
            ProductType.SUBSCRIPTION -> StoreProductType.SUBSCRIPTION
        }

    private fun PurchaseType.toStorePurchaseType(): StorePurchaseType =
        when (this) {
            PurchaseType.ONE_STEP -> StorePurchaseType.ONE_STEP
            PurchaseType.TWO_STEP -> StorePurchaseType.TWO_STEP
            PurchaseType.UNDEFINED -> StorePurchaseType.UNDEFINED
        }

    private fun ProductPurchaseResult.toStoreProductPurchaseResult(): StoreProductPurchaseResult =
        StoreProductPurchaseResult(
            invoiceId = this.invoiceId.value,
            orderId = this.orderId?.value,
            productId = this.productId.value,
            productType = this.productType.toStoreProductType(),
            purchaseId = this.purchaseId.value,
            purchaseType = this.purchaseType.toStorePurchaseType(),
            quantity = this.quantity.value,
            sandbox = this.sandbox,
        )

    private fun Purchase.toStorePurchase(): StorePurchase = object : StorePurchase {
        override val amountLabel: String = this@toStorePurchase.amountLabel.value
        override val currency: String = this@toStorePurchase.currency.value
        override val description: String = this@toStorePurchase.description.value
        override val developerPayload: String? = this@toStorePurchase.developerPayload?.value
        override val invoiceId: String = this@toStorePurchase.invoiceId.value
        override val orderId: String? = this@toStorePurchase.orderId?.value
        override val price: Int = this@toStorePurchase.price.value
        override val purchaseId: String = this@toStorePurchase.purchaseId.value
        override val purchaseTime: Long? = this@toStorePurchase.purchaseTime?.time
        override val purchaseType: StorePurchaseType =
            this@toStorePurchase.purchaseType.toStorePurchaseType()
        override val sandbox: Boolean = this@toStorePurchase.sandbox
        override val status: StorePurchaseStatus =
            purchaseStatusMapper.mapToModel(this@toStorePurchase.status)
    }
}