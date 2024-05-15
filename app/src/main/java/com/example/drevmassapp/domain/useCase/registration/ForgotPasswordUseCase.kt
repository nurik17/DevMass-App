package com.example.drevmassapp.domain.useCase.registration

import com.example.drevmassapp.data.model.ForgotPasswordDto

interface ForgotPasswordUseCase {
    suspend fun forgotPassword(email: String): ForgotPasswordDto
}