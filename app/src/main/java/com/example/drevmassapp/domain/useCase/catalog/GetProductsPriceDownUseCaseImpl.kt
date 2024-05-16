package com.example.drevmassapp.domain.useCase.catalog

import com.example.drevmassapp.data.model.ProductX
import com.example.drevmassapp.domain.repository.CatalogRepository
import javax.inject.Inject

class GetProductsPriceDownUseCaseImpl @Inject constructor(
    private val repository: CatalogRepository
): GetProductsPriceDownUseCase {
    override suspend fun getProductsPriceDown(token: String): ProductX {
        return repository.getProductsPriceDown(token)
    }
}