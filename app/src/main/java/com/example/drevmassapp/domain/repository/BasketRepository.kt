package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.data.model.BasketResponseDto
import com.example.drevmassapp.data.model.ForgotPasswordDto

interface BasketRepository {
    suspend fun getBasket(token: String,isUsing: String): BasketResponseDto
    suspend fun deleteAllBasketItems(token: String): ForgotPasswordDto
}