package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.data.model.SignUpResponseDto

interface RegistrationRepository {
    suspend fun signUp(
        deviceToken: String,
        email: String,
        name: String,
        password: String,
        phoneNumber: String
    ): SignUpResponseDto
}