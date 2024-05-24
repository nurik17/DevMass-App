package com.example.drevmassapp.data.local

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.data.model.ProductDetailDto
import com.example.drevmassapp.data.model.ProductX
import com.example.drevmassapp.data.remote.DevMassApi
import com.example.drevmassapp.domain.entity.AddToBasketBody
import com.example.drevmassapp.domain.repository.CatalogRepository
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val api: DevMassApi
) : CatalogRepository {

    override suspend fun getProducts(token: String): ProductX {
        return api.getProducts(getToken(token))
    }

    override suspend fun getFamousProducts(token: String): ProductX {
        return api.getFamousProducts(getToken(token))

    }

    override suspend fun getProductsPriceDown(token: String): ProductX {
        return api.getProductsPriceDown(getToken(token))
    }

    override suspend fun getProductsPriceUp(token: String): ProductX {
        return api.getProductsPriceUp(getToken(token))
    }

    override suspend fun getProductById(token: String, id: Int): ProductDetailDto {
        return api.getProductById(getToken(token), id)
    }

    override suspend fun addToBasket(
        token: String,
        count: Int,
        productId: Int,
        userId: Int
    ): ForgotPasswordDto {
        val body = AddToBasketBody(count, productId, userId)
        return api.addToBasket(getToken(token),body)
    }

    override suspend fun increaseBasketItem(
        token: String,
        count: Int,
        productId: Int,
        userId: Int
    ): ForgotPasswordDto {
        val body = AddToBasketBody(count, productId, userId)
        return api.increaseBasketItem(getToken(token),body)
    }

    override suspend fun decreaseBasketItem(
        token: String,
        count: Int,
        productId: Int,
        userId: Int
    ): ForgotPasswordDto {
        val body = AddToBasketBody(count, productId, userId)
        return api.decreaseBasketItem(getToken(token),body)
    }

    private fun getToken(token: String): String {
        return "Bearer $token"
    }

}