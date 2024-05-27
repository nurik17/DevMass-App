package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BonusDto(
    @SerializedName("Burning") val burning: List<Any>,
    @SerializedName("Transactions") val transactions: List<Transaction>,
    @SerializedName("bonus") val bonus: Int,
    @SerializedName("user_id") val userId: Int
)