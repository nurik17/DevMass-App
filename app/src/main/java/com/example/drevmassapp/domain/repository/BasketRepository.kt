package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.data.model.BasketResponseDto
import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.domain.entity.OrderedProduct

interface BasketRepository {
    suspend fun getBasket(token: String, isUsing: String): BasketResponseDto
    suspend fun deleteAllBasketItems(token: String): ForgotPasswordDto

    suspend fun makeOrder(
        token: String,
        bonus: Int,
        crmLink: String,
        email: String,
        phoneNumber: String,
        products: List<OrderedProduct>,
        totalPrice: Int,
        userName: String
    ): ForgotPasswordDto
}