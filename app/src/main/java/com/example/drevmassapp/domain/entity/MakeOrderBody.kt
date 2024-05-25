package com.example.drevmassapp.domain.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MakeOrderBody(
    @SerializedName("bonus") val bonus: Int,
    @SerializedName("crmlink") val crmLink: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("products") val products: List<OrderedProduct>,
    @SerializedName("total_price") val totalPrice: Int,
    @SerializedName("username") val username: String
)