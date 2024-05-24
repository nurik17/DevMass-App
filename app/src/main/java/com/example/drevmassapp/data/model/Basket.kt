package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Basket(
    @SerializedName("count") val count: Int,
    @SerializedName("price") val price: Int,
    @SerializedName("product_id") val productId: Int,
    @SerializedName("product_img") val productImg: String,
    @SerializedName("product_title") val productTitle: String
)