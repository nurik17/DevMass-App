package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BasketResponseDto(
    @SerializedName("basket") val basket: List<Basket>,
    @SerializedName("basket_price") val basketPrice: Int,
    @SerializedName("bonus") val bonus: Int,
    @SerializedName("count_products") val countProducts: Int,
    @SerializedName("discount") val discount: Int,
    @SerializedName("products") val products: List<Product>,
    @SerializedName("total_price") val totalPrice: Int,
    @SerializedName("used_bonus") val usedBonus: Int
)