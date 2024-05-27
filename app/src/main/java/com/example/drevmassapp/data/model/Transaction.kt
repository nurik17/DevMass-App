package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Transaction(
    @SerializedName("bonus_id") val bonusId: Int,
    @SerializedName("description") val description: String,
    @SerializedName("promo_price") val promoPrice: Int,
    @SerializedName("promo_type") val promoType: String,
    @SerializedName("transaction_date") val transactionDate: String,
    @SerializedName("transaction_type") val transactionType: String,
    @SerializedName("userid") val userId: Int
)