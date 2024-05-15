package com.example.drevmassapp.data.local

import com.example.drevmassapp.data.model.SignUpResponseDto
import com.example.drevmassapp.data.model.SignInResponseDto
import com.example.drevmassapp.data.remote.DevMassApi
import com.example.drevmassapp.domain.entity.SignInBody
import com.example.drevmassapp.domain.entity.SignUpBody
import com.example.drevmassapp.domain.repository.RegistrationRepository
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

    override suspend fun login(
        deviceToken: String,
        email: String,
        password: String
    ): SignInResponseDto {
        val body = SignInBody(deviceToken, email, password)
        return api.login(body)
    }

}