package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.BonusInfoDto
import com.example.drevmassapp.domain.repository.ProfileRepository
import javax.inject.Inject

class GetSupportInfoUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
): GetSupportInfoUseCase {
    override suspend fun getSupportInfo(token: String): BonusInfoDto {
        return repository.getSupportInfo(token)
    }
}