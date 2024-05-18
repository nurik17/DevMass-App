package com.example.drevmassapp.domain.useCase.catalog

import com.example.drevmassapp.data.model.ProductDetailDto
import com.example.drevmassapp.domain.repository.CatalogRepository
import javax.inject.Inject

class GetProductByIdUseCaseImpl @Inject constructor(
    private val repository: CatalogRepository
): GetProductByIdUseCase {
    override suspend fun getProductById(token: String, id: Int): ProductDetailDto {
        return repository.getProductById(token, id)
    }
}