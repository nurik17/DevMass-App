package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.data.model.SignUpResponseDto
import com.example.drevmassapp.data.model.SignInResponseDto

interface RegistrationRepository {
    suspend fun signUp(
        deviceToken: String,
        email: String,
        name: String,
        password: String,
        phoneNumber: String
    ): SignUpResponseDto

    suspend fun login(deviceToken: String, email: String, password: String): SignInResponseDto
}