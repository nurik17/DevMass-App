package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteUserAccountUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
): DeleteUserAccountUseCase {
    override suspend fun deleteUserAccount(token: String): ForgotPasswordDto {
        return repository.deleteUserAccount(token)
    }
}