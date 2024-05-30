package com.example.drevmassapp.domain.repository

import com.example.drevmassapp.data.model.BonusDto
import com.example.drevmassapp.data.model.BonusInfoDto
import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.data.model.Promocode
import com.example.drevmassapp.data.model.UserDto
import com.example.drevmassapp.domain.entity.UpdateUserBody

interface ProfileRepository {
    suspend fun getUser(token: String): UserDto
    suspend fun getBonus(token: String): BonusDto
    suspend fun getBonusInfo(token: String): BonusInfoDto
    suspend fun getPromocodeInfo(token: String): Promocode
    suspend fun getSupportInfo(token: String): BonusInfoDto
    suspend fun sendSupportText(token: String, message: String): ForgotPasswordDto
    suspend fun updateUserData(
        token: String,
        activity: Int,
        birth: String,
        email: String,
        gender: Int,
        height: Int,
        id: Int,
        name: String,
        phoneNumber: String,
        weight: Int
    ): UpdateUserBody

    suspend fun resetPassword(
        token: String,
        currentPassword: String,
        newPassword: String
    ): ForgotPasswordDto

    suspend fun deleteUserAccount(token: String): ForgotPasswordDto
}