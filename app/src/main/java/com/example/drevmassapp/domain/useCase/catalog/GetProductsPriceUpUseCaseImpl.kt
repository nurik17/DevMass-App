package com.example.drevmassapp.domain.useCase.catalog

import com.example.drevmassapp.data.model.ProductX
import com.example.drevmassapp.domain.repository.CatalogRepository
import javax.inject.Inject

class GetProductsPriceUpUseCaseImpl @Inject constructor(
    private val repository: CatalogRepository
): GetProductsPriceUpUseCase {
    override suspend fun getProductsPriceUp(token: String): ProductX {
        return repository.getProductsPriceUp(token)
    }
}