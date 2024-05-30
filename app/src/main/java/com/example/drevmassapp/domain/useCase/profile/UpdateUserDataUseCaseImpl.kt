package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.domain.entity.UpdateUserBody
import com.example.drevmassapp.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateUserDataUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : UpdateUserDataUseCase {
    override suspend fun updateUserData(
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
    ): UpdateUserBody {
        return repository.updateUserData(
            token,
            activity,
            birth,
            email,
            gender,
            height,
            id,
            name,
            phoneNumber,
            weight
        )
    }
}