package com.example.drevmassapp.domain.useCase.registration

import com.example.drevmassapp.data.model.SignInResponseDto

interface LoginUseCase {
    suspend fun login(deviceToken: String, email: String, password: String): SignInResponseDto
}