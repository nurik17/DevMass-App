package com.example.drevmassapp.domain.useCase.basket

import com.example.drevmassapp.data.model.ForgotPasswordDto

interface DeleteAllBasketItemsUseCase {
    suspend fun deleteAllBasketItems(token: String): ForgotPasswordDto
}