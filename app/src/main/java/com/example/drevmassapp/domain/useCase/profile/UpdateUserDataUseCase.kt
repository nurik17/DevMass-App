package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.domain.entity.UpdateUserBody

interface UpdateUserDataUseCase {
    suspend fun updateUserData(
        token: String,
        activity: Int,
        birth: String,
        email: String,
        gender: Int,
        height: Int,
        id: Int,
        name: String,
        phoneNumber: String,
        weight: Int
    ): UpdateUserBody
}