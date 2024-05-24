package com.example.drevmassapp.domain.useCase.catalog.detail

import com.example.drevmassapp.data.model.ForgotPasswordDto

interface IncreaseItemUseCase {
    suspend fun increaseBasketItem(token: String,count: Int,productId: Int,userId: Int): ForgotPasswordDto
}