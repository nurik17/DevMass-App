package com.example.drevmassapp.domain.useCase.catalog.detail

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.domain.repository.CatalogRepository
import javax.inject.Inject

class DecreaseItemUseCaseImpl @Inject constructor(
    private val repository: CatalogRepository
): DecreaseItemUseCase {
    override suspend fun decreaseBasketItem(
        token: String,
        count: Int,
        productId: Int,
        userId: Int
    ): ForgotPasswordDto {
        return repository.decreaseBasketItem(token, count, productId, userId)
    }
}