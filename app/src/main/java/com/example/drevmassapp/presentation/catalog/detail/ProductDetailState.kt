package com.example.drevmassapp.presentation.catalog.detail

import com.example.drevmassapp.data.model.ProductDetailDto

sealed interface ProductDetailState {
    object Initial : ProductDetailState
    object Loading : ProductDetailState
    data class Failure(val message: String) : ProductDetailState
    data class Success(val productDetail: ProductDetailDto) : ProductDetailState
}