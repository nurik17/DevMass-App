package com.example.drevmassapp.data.local

import com.example.drevmassapp.data.model.BasketResponseDto
import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.data.remote.DrevMassApi
import com.example.drevmassapp.domain.entity.MakeOrderBody
import com.example.drevmassapp.domain.entity.OrderedProduct
import com.example.drevmassapp.domain.repository.BasketRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val api: DrevMassApi
) : BasketRepository {
    override suspend fun getBasket(token: String, isUsing: String): BasketResponseDto {
        return api.getBasket(getToken(token), isUsing)
    }

    override suspend fun deleteAllBasketItems(token: String): ForgotPasswordDto {
        return api.deleteAllBasketItems(getToken(token))
    }

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
        val body = MakeOrderBody(bonus,crmLink,email,phoneNumber,products,totalPrice,userName)
        return api.makeOrder(getToken(token),body)
    }

    private fun getToken(token: String): String {
        return "Bearer $token"
    }
}