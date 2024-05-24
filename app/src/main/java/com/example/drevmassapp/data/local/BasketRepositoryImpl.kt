package com.example.drevmassapp.data.local

import com.example.drevmassapp.data.model.BasketResponseDto
import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.data.remote.DevMassApi
import com.example.drevmassapp.domain.repository.BasketRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val api: DevMassApi
): BasketRepository {
    override suspend fun getBasket(token: String, isUsing: String): BasketResponseDto {
        delay(2000L)
        return api.getBasket(getToken(token),isUsing)
    }

    override suspend fun deleteAllBasketItems(token: String): ForgotPasswordDto {
        return api.deleteAllBasketItems(getToken(token))
    }

    private fun getToken(token: String): String {
        return "Bearer $token"
    }
}