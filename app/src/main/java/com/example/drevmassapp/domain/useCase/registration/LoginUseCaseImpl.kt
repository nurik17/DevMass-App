package com.example.drevmassapp.domain.useCase.registration

import com.example.drevmassapp.data.model.SignInResponseDto
import com.example.drevmassapp.domain.repository.RegistrationRepository
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
): LoginUseCase {
    override suspend fun login(
        deviceToken: String,
        email: String,
        password: String
    ): SignInResponseDto {
        return repository.login(deviceToken, email, password)
    }
}