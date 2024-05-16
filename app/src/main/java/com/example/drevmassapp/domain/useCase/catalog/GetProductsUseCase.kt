package com.example.drevmassapp.domain.useCase.catalog

import com.example.drevmassapp.data.model.Product
import com.example.drevmassapp.data.model.ProductX

interface GetProductsUseCase {
    suspend fun getProducts(token: String): ProductX
}