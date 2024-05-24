package com.example.drevmassapp.domain.useCase.basket

import com.example.drevmassapp.data.model.BasketResponseDto

interface GetBasketUseCase {
    suspend fun getBasket(token: String,isUsing: String): BasketResponseDto
}