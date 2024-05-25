package com.example.drevmassapp.domain.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderedProduct(
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("product_id") val productId: Int,
    @SerializedName("quantity") val quantity: Int
)