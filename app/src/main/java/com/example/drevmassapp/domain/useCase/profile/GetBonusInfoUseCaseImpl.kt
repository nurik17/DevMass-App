package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.BonusInfoDto
import com.example.drevmassapp.domain.repository.ProfileRepository
import javax.inject.Inject

class GetBonusInfoUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
): GetBonusInfoUseCase {
    override suspend fun getBonusInfo(token: String): BonusInfoDto{
        return repository.getBonusInfo(token)
    }
}