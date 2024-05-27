package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.BonusDto

interface GetBonusUseCase {
    suspend fun getBonus(token: String): BonusDto
}