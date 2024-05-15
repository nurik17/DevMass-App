package com.example.drevmassapp.domain.useCase.registration

import com.example.drevmassapp.data.model.SignUpResponseDto
import com.example.drevmassapp.domain.repository.RegistrationRepository
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
): SingUpUseCase {
    override suspend fun signUp(
        deviceToken: String,
        email: String,
        name: String,
        password: String,
        phoneNumber: String
    ): SignUpResponseDto {
        return repository.signUp(deviceToken, email, name, password, phoneNumber)
    }
}