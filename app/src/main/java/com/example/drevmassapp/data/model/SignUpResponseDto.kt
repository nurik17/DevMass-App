package com.example.drevmassapp.data.model

import com.google.gson.annotations.SerializedName

data class SignUpResponseDto(
    @SerializedName("access_token") val accessToken: String
)