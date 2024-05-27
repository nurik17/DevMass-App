package com.example.drevmassapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Promocode(
    @SerializedName("all_attempt") val allAttempt: Int,
    @SerializedName("bonus") val bonus: Int,
    @SerializedName("description") val description: String,
    @SerializedName("promocode") val promocode: String,
    @SerializedName("used") val used: Int
)