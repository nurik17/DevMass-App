package com.example.drevmassapp.domain.useCase.catalog.detail

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.domain.repository.CatalogRepository
import javax.inject.Inject

class IncreaseItemUseCaseImpl @Inject constructor(
    private val repository: CatalogRepository
): IncreaseItemUseCase {
    override suspend fun increaseBasketItem(
        token: String,
        count: Int,
        productId: Int,
        userId: Int
    ): ForgotPasswordDto {
        return repository.increaseBasketItem(token, count, productId, userId)
    }
}