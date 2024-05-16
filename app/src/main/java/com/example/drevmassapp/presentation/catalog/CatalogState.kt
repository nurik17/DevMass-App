package com.example.drevmassapp.presentation.catalog

import com.example.drevmassapp.data.model.ProductX


sealed interface CatalogState {
    object Initial : CatalogState
    object Loading : CatalogState
    data class Failure(val message: String) : CatalogState

    data class Products(val products: ProductX) : CatalogState
    data class FamousProducts(val famousProducts: ProductX) : CatalogState
    data class ProductsPriceDown(val productsPriceDown: ProductX) : CatalogState
    data class ProductsPriceUp(val productsPriceUp: ProductX) : CatalogState
}