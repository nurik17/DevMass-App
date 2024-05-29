package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.BonusInfoDto

interface GetSupportInfoUseCase  {
    suspend fun getSupportInfo(token: String): BonusInfoDto
}