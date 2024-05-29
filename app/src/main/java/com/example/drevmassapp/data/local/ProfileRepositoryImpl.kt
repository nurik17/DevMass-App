package com.example.drevmassapp.data.local

import com.example.drevmassapp.data.model.BonusDto
import com.example.drevmassapp.data.model.BonusInfoDto
import com.example.drevmassapp.data.model.Promocode
import com.example.drevmassapp.data.model.UserDto
import com.example.drevmassapp.data.remote.DrevMassApi
import com.example.drevmassapp.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: DrevMassApi
): ProfileRepository {
    override suspend fun getUser(token: String): UserDto {

        return api.getUser(getToken(token))
    }

    override suspend fun getBonus(token: String): BonusDto {
        return api.getBonus(getToken(token))
    }

    override suspend fun getBonusInfo(token: String): BonusInfoDto {
        return api.getBonusInfo(getToken(token))
    }

    override suspend fun getPromocodeInfo(token: String): Promocode {
        return api.getPromocodeInfo(getToken(token))
    }

    override suspend fun getSupportInfo(token: String): BonusInfoDto {
        return api.getSupportInfo(getToken(token))
    }

    private fun getToken(token: String): String {
        return "Bearer $token"
    }

}