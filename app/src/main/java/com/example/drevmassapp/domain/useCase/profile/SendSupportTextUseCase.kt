package com.example.drevmassapp.domain.useCase.profile

import com.example.drevmassapp.data.model.ForgotPasswordDto

interface SendSupportTextUseCase {
    suspend fun sendSupportText(token: String,message: String): ForgotPasswordDto
}