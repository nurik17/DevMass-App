package com.example.drevmassapp.data.local

import com.example.drevmassapp.data.model.UserDto
import com.example.drevmassapp.data.remote.DevMassApi
import com.example.drevmassapp.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: DevMassApi
): ProfileRepository {
    override suspend fun getUser(token: String): UserDto {
        return api.getUser(getToken(token))
    }
    private fun getToken(token: String): String {
        return "Bearer $token"
    }
}