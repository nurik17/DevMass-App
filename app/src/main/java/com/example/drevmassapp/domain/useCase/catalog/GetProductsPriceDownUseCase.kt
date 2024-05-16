package com.example.drevmassapp.domain.useCase.catalog

import com.example.drevmassapp.data.model.ProductX

interface GetProductsPriceDownUseCase {
    suspend fun getProductsPriceDown(token: String): ProductX
}