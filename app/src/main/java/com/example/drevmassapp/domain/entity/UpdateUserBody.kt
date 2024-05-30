package com.example.drevmassapp.domain.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UpdateUserBody(
    @SerializedName("activity") val activity: Int,
    @SerializedName("birth") val birth: String,
    @SerializedName("email") val email: String,
    @SerializedName("gender") val gender: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("weight") val weight: Int
)