package com.example.drevmassapp.domain.useCase.catalog.detail

import com.example.drevmassapp.data.model.ForgotPasswordDto

interface AddToBasketUseCase {
    suspend fun addToBasket(token: String,count: Int,productId: Int,userId: Int): ForgotPasswordDto
}