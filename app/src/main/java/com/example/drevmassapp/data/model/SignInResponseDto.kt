package com.example.drevmassapp.data.model

import com.google.gson.annotations.SerializedName

data class SignInResponseDto(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)