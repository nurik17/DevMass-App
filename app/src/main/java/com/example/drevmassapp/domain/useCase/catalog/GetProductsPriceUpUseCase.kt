package com.example.drevmassapp.domain.useCase.catalog

import com.example.drevmassapp.data.model.ProductX

interface GetProductsPriceUpUseCase {
    suspend fun getProductsPriceUp(token: String): ProductX
}