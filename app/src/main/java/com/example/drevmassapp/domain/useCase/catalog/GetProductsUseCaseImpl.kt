package com.example.drevmassapp.domain.useCase.catalog

import com.example.drevmassapp.data.model.ProductX
import com.example.drevmassapp.domain.repository.CatalogRepository
import javax.inject.Inject

class GetProductsUseCaseImpl @Inject constructor(
    private val repository: CatalogRepository
): GetProductsUseCase {
    override suspend fun getProducts(token: String): ProductX {
        return repository.getProducts(token)
    }
}