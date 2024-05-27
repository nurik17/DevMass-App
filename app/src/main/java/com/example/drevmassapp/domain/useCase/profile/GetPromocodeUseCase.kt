package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.Promocode

interface GetPromocodeUseCase {
    suspend fun getPromocodeInfo(token: String): Promocode
}