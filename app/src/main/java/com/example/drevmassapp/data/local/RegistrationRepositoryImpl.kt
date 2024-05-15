package com.example.drevmassapp.data.local

import com.example.drevmassapp.data.model.SignUpResponseDto
import com.example.drevmassapp.data.remote.DevMassApi
import com.example.drevmassapp.domain.entity.SignUpBody
import com.example.drevmassapp.domain.repository.RegistrationRepository
import kotlinx.coroutines.delay
import retrofit2.Response
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: DevMassApi
): RegistrationRepository {

    override suspend fun signUp(
        deviceToken: String,
        email: String,
        name: String,
        password: String,
        phoneNumber: String
    ): SignUpResponseDto {
        val body = SignUpBody(deviceToken,email,name,password,phoneNumber)
        return api.singUp(body)
    }
}