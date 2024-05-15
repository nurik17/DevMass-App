package com.example.drevmassapp.domain.entity

data class SignInBody(
    val deviceToken: String,
    val email: String,
    val password: String
)