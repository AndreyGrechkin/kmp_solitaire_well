package com.defey.solitairewell.products

import com.defey.solitairewell.models.ProductPurchaseStatus
import com.defey.solitairewell.models.StoreProduct
import com.defey.solitairewell.purchases.PurchaseStatusMapper
import ru.rustore.sdk.pay.model.Product
import ru.rustore.sdk.pay.model.ProductId
import ru.rustore.sdk.pay.model.ProductPurchase
import ru.rustore.sdk.pay.model.Purchase
import ru.rustore.sdk.pay.model.SubscriptionPurchase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class StoreProductConverter(
    private val purchaseStatusMapper: PurchaseStatusMapper,
) {

    fun convert(products: List<Product>, purchases: List<Purchase>): List<StoreProduct> =
        products.map { product ->
            StoreProduct(
                productId = product.productId.value,
                title = product.title.value,
                description = product.description?.value,
                purchaseStatus = getProductPurchaseStatus(product.productId, purchases),
                price = product.price?.value ?: 0,
            )
        }

    private fun getProductPurchaseStatus(
        productId: ProductId,
        purchases: List<Purchase>,
    ): ProductPurchaseStatus =
        purchases
            .sortedByDescending { purchase ->
                purchaseStatusMapper.mapToModel(purchase.status).toString()
            }
            .firstOrNull { purchase ->
                when (purchase) {
                    is ProductPurchase -> purchase.productId == productId
                    is SubscriptionPurchase -> purchase.productId == productId
                    else -> false
                }
            }
            ?.let(::mapPayStatusToExist)
            ?: ProductPurchaseStatus.NotExist

    private fun mapPayStatusToExist(purchase: Purchase): ProductPurchaseStatus.Exist =
        ProductPurchaseStatus.Exist(
            purchaseId = purchase.purchaseId.value,
            purchaseStatus = purchaseStatusMapper.mapToModel(purchase.status),
            developerPayload = null,
        )
}