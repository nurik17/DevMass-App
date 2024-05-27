package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.Promocode
import com.example.drevmassapp.domain.repository.ProfileRepository
import javax.inject.Inject

class GetPromocodeUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : GetPromocodeUseCase {
    override suspend fun getPromocodeInfo(token: String): Promocode {
        return repository.getPromocodeInfo(token)
    }
}