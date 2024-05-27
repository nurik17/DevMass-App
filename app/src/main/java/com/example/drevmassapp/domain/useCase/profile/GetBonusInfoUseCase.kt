package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.BonusInfoDto

interface GetBonusInfoUseCase {
    suspend fun getBonusInfo(token: String): BonusInfoDto
}