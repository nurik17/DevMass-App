package com.example.drevmassapp.domain.entity

import com.google.gson.annotations.SerializedName


data class SignUpBody(
    val deviceToken: String,
    val email: String,
    val name: String,
    val password: String,
    @SerializedName("phone_number")val phoneNumber: String
)