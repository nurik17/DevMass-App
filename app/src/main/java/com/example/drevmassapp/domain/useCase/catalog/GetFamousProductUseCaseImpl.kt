package com.example.drevmassapp.domain.useCase.catalog

import com.example.drevmassapp.data.model.ProductX
import com.example.drevmassapp.domain.repository.CatalogRepository
import javax.inject.Inject

class GetFamousProductUseCaseImpl @Inject constructor(
    private val repository: CatalogRepository
): GetFamousProductUseCase {
    override suspend fun getFamousProducts(token: String): ProductX {
        return repository.getFamousProducts(token)
    }
}