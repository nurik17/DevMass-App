package com.example.drevmassapp.domain.useCase.catalog.detail

import com.example.drevmassapp.data.model.ForgotPasswordDto

interface DecreaseItemUseCase {
    suspend fun decreaseBasketItem(token: String,count: Int,productId: Int,userId: Int): ForgotPasswordDto
}