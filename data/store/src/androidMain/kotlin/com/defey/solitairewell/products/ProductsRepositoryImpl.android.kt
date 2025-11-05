package com.defey.solitairewell.products

import com.defey.solitairewell.managers.billing.ProductsRepository
import com.defey.solitairewell.managers.billing.StorePurchaseStatus
import com.defey.solitairewell.models.ProductArticle
import com.defey.solitairewell.models.ProductPurchaseStatus
import com.defey.solitairewell.models.StoreProduct
import com.defey.solitairewell.purchases.PurchasesDataSource

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ProductsRepositoryImpl(
    private val productsDataSource: ProductsDataSource,
    private val purchasesDataSource: PurchasesDataSource,
    private val converter: StoreProductConverter,
) : ProductsRepository {

    actual override suspend fun get(productsIds: List<String>): List<StoreProduct> =
        converter.convert(
            productsDataSource.get(productsIds),
            purchasesDataSource.get(productType = null, purchaseStatus = null),
        )

    actual override suspend fun checkPurchasedProduct(product: ProductArticle): Boolean {
        val product =
            get(listOf(product.id)).firstOrNull { it.productId == product.id } ?: return false
        return when (val status = product.purchaseStatus) {
            is ProductPurchaseStatus.Exist -> {
                status.purchaseStatus == StorePurchaseStatus.ProductPurchaseStatus.CONFIRMED
            }

            ProductPurchaseStatus.NotExist -> false
        }
    }
}