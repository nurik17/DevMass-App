package com.example.drevmassapp.domain.useCase.catalog

import com.example.drevmassapp.data.model.ProductDetailDto

interface GetProductByIdUseCase {
    suspend fun getProductById(token: String,id: Int): ProductDetailDto

}