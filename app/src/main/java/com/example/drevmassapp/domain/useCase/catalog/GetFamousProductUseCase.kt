package com.example.drevmassapp.domain.useCase.catalog

import com.example.drevmassapp.data.model.ProductX

interface GetFamousProductUseCase {
    suspend fun getFamousProducts(token: String): ProductX
}