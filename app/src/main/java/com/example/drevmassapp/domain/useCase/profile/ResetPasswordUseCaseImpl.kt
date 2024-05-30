package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.domain.repository.ProfileRepository
import javax.inject.Inject

class ResetPasswordUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
): ResetPasswordUseCase {
    override suspend fun resetPassword(
        token: String,
        currentPassword: String,
        newPassword: String
    ): ForgotPasswordDto {
        return repository.resetPassword(token, currentPassword, newPassword)
    }
}