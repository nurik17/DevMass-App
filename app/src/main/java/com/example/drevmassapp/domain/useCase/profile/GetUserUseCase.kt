package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.UserDto

interface GetUserUseCase {
    suspend fun getUser(token: String): UserDto
}