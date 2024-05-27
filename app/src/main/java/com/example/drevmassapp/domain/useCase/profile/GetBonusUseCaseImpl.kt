package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.BonusDto
import com.example.drevmassapp.domain.repository.ProfileRepository
import javax.inject.Inject

class GetBonusUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
): GetBonusUseCase {
    override suspend fun getBonus(token: String): BonusDto {
        return repository.getBonus(token)
    }
}