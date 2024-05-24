package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.data.model.ProductDetailDto
import com.example.drevmassapp.data.model.ProductX

interface CatalogRepository {
    suspend fun getProducts(token: String): ProductX
    suspend fun getFamousProducts(token: String): ProductX
    suspend fun getProductsPriceDown(token: String): ProductX
    suspend fun getProductsPriceUp(token: String): ProductX
    suspend fun getProductById(token: String,id: Int): ProductDetailDto
    suspend fun addToBasket(token: String,count: Int,productId: Int,userId: Int): ForgotPasswordDto
    suspend fun increaseBasketItem(token: String,count: Int,productId: Int,userId: Int): ForgotPasswordDto
    suspend fun decreaseBasketItem(token: String,count: Int,productId: Int,userId: Int): ForgotPasswordDto
}