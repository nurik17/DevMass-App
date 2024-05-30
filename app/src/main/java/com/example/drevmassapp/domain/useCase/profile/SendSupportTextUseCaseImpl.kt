package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.domain.repository.ProfileRepository
import javax.inject.Inject

class SendSupportTextUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : SendSupportTextUseCase {
    override suspend fun sendSupportText(token: String, message: String): ForgotPasswordDto {
        return repository.sendSupportText(token, message)
    }
}