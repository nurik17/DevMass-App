package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.ForgotPasswordDto

interface ResetPasswordUseCase {
    suspend fun resetPassword(
        token: String,
        currentPassword: String,
        newPassword: String
    ): ForgotPasswordDto
}