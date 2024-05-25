package com.example.drevmassapp.domain.useCase.basket

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.domain.entity.OrderedProduct
import com.example.drevmassapp.domain.repository.BasketRepository
import javax.inject.Inject

class MakeOrderUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
): MakeOrderUseCase{
    override suspend fun makeOrder(
        token: String,
        bonus: Int,
        crmLink: String,
        email: String,
        phoneNumber: String,
        products: List<OrderedProduct>,
        totalPrice: Int,
        userName: String
    ): ForgotPasswordDto {
        return repository.makeOrder(token, bonus, crmLink, email, phoneNumber, products, totalPrice, userName)
    }
}