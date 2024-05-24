package com.example.drevmassapp.domain.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AddToBasketBody(
    @SerializedName("count") val count: Int,
    @SerializedName("product_id") val productId: Int,
    @SerializedName("user_id") val userId: Int
)