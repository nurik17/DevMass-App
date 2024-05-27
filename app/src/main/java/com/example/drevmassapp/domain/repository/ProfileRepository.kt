package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.data.model.BonusDto
import com.example.drevmassapp.data.model.BonusInfoDto
import com.example.drevmassapp.data.model.UserDto

interface ProfileRepository{
    suspend fun getUser(token: String): UserDto
    suspend fun getBonus(token: String): BonusDto
    suspend fun getBonusInfo(token: String): BonusInfoDto
}