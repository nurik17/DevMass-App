package com.example.drevmassapp.domain.useCase.basket

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.domain.entity.OrderedProduct

interface MakeOrderUseCase {
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