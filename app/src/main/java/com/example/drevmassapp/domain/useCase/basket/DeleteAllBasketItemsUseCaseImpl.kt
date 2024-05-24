package com.example.drevmassapp.domain.useCase.basket

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.domain.repository.BasketRepository
import javax.inject.Inject

class DeleteAllBasketItemsUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
): DeleteAllBasketItemsUseCase {
    override suspend fun deleteAllBasketItems(token: String): ForgotPasswordDto {
        return repository.deleteAllBasketItems(token)
    }
}