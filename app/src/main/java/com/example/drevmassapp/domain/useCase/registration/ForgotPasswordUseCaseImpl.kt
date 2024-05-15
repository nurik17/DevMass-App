package com.example.drevmassapp.domain.useCase.registration

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.domain.repository.RegistrationRepository
import javax.inject.Inject

class ForgotPasswordUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
): ForgotPasswordUseCase {
    override suspend fun forgotPassword(email: String): ForgotPasswordDto {
        return repository.forgotPassword(email = email)
    }
}