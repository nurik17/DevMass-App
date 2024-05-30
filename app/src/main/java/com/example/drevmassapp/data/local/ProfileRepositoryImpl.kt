package com.example.drevmassapp.data.local

import com.example.drevmassapp.data.model.BonusDto
import com.example.drevmassapp.data.model.BonusInfoDto
import com.example.drevmassapp.data.model.ForgotPasswordDto
import com.example.drevmassapp.data.model.Promocode
import com.example.drevmassapp.data.model.UserDto
import com.example.drevmassapp.data.remote.DrevMassApi
import com.example.drevmassapp.domain.entity.ResetPasswordBody
import com.example.drevmassapp.domain.entity.UpdateUserBody
import com.example.drevmassapp.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: DrevMassApi
) : ProfileRepository {
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

    override suspend fun sendSupportText(token: String, message: String): ForgotPasswordDto {
        return api.sendSupportText(getToken(token), message)
    }

    override suspend fun updateUserData(
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
    ): UpdateUserBody {
        val body =
            UpdateUserBody(activity, birth, email, gender, height, id, name, phoneNumber, weight)
        return api.updateUserData(getToken(token), body)
    }

    override suspend fun resetPassword(
        token: String,
        currentPassword: String,
        newPassword: String
    ): ForgotPasswordDto {
        val body = ResetPasswordBody(currentPassword, newPassword)
        return api.resetPassword(getToken(token), body)
    }

    override suspend fun deleteUserAccount(token: String): ForgotPasswordDto {
        return api.deleteUserAccount(getToken(token))
    }

    private fun getToken(token: String): String {
        return "Bearer $token"
    }

}