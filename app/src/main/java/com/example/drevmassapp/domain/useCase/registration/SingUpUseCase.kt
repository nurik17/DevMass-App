package com.example.drevmassapp.domain.useCase.registration

import com.example.drevmassapp.data.model.SignUpResponseDto
import retrofit2.Response

interface SingUpUseCase {
    suspend fun signUp(
        deviceToken: String,
        email: String,
        name: String,
        password: String,
        phoneNumber: String
    ): SignUpResponseDto
}