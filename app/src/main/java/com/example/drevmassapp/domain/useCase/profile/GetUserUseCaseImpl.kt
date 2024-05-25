package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.UserDto
import com.example.drevmassapp.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
): GetUserUseCase {
    override suspend fun getUser(token: String): UserDto {
        return repository.getUser(token)
    }
}