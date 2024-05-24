package com.example.drevmassapp.domain.useCase.catalog.detail

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.domain.repository.CatalogRepository
import javax.inject.Inject

class AddToBasketUseCaseImpl @Inject constructor(
    private val repository: CatalogRepository
): AddToBasketUseCase {
    override suspend fun addToBasket(
        token: String,
        count: Int,
        productId: Int,
        userId: Int
    ): ForgotPasswordDto {
        return repository.addToBasket(token, count, productId, userId)
    }
}